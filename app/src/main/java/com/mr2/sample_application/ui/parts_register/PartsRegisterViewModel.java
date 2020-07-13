package com.mr2.sample_application.ui.parts_register;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.mr2.sample_app_application.parts_register.PartsRegisterApplicationService;
import com.mr2.sample_app_application.parts_register.PartsRegisterDto;
import com.mr2.sample_app_domain.parts.Parts;
import com.mr2.sample_app_infra.room_database.parts.PartsEntity;
import com.mr2.sample_app_infra.ui_resource.SingleStringListResource;
import com.mr2.sample_app_infra.ui_resource.parts_register.MakerModelListDto;
import com.mr2.sample_application.Executors;
import com.mr2.sample_application.MyApplication;

import java.util.ArrayList;
import java.util.List;

public class PartsRegisterViewModel extends AndroidViewModel {
    private MyApplication app;
    private PartsRegisterApplicationService service;

    public static final String CURRENT_CURRENCY = "円";

    public MutableLiveData<ViewModelState> state = new MutableLiveData<>(ViewModelState.loading);
    public MutableLiveData<String> errorMessage = new MutableLiveData<>("");
    public MediatorLiveData<Boolean> hasProgress = new MediatorLiveData<>();
    // core info
    public MutableLiveData<String> maker = new MutableLiveData<>("");
    public LiveData<List<SingleStringListResource>> makerList;
    public MutableLiveData<String> model = new MutableLiveData<>("");
    public LiveData<List<MakerModelListDto>> makerModelList;
    public MutableLiveData<List<SingleStringListResource>> modelList = new MutableLiveData<>();
    public MutableLiveData<Boolean> isValidCoreInfo = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> isDuplicate = new MutableLiveData<>(false);
    // info 1
    public MutableLiveData<String> name = new MutableLiveData<>("");
    public MutableLiveData<Boolean> isValidPartsName = new MutableLiveData<>(false);
    // info 2
    public MutableLiveData<String> unit = new MutableLiveData<>("");
    public LiveData<List<SingleStringListResource>> unitList;
    public MutableLiveData<String> priceString = new MutableLiveData<>("0");
    public MutableLiveData<Float> price = new MutableLiveData<>((float) 0);
    public MutableLiveData<Boolean> isValidPriceInfo = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> isNotNumeric = new MutableLiveData<>(false);

    public PartsRegisterViewModel(@NonNull Application application) {
        super(application);
        app = (MyApplication) application;
        service = app.component.getPartsRegisterApplicationService();
//        setDefaultData();

        // suggest data
        makerList = app.db.partsRegisterResourceDao().getMakerList();
        makerModelList = app.db.partsRegisterResourceDao().getMakerModelList();
        unitList = app.db.partsRegisterResourceDao().getUnitList();
    }

    void setDefaultData(){
        for (int i = 0; i < 30; i++) {
            int m = i % 3;
            String unit;
            switch (m){
                case 0:
                    unit = "個";
                    break;
                case 1:
                    unit = "箱";
                    break;
                case 2:
                    unit = "本";
                    break;
                default:
                    unit = "不明";
            }
            service.registerNewParts(new PartsRegisterDto()
                    .setMaker("メーカー:" + m)
                    .setModel("model:" + i)
                    .setName("品名:" + i)
                    .setValue(100.0f * i)
                    .setCurrency(CURRENT_CURRENCY)
                    .setUnit(unit)
            );
//            PartsEntity e = new PartsEntity(
//                    0,
//                    0,
//                    "品名" + i,
//                    "model" + i,
//                    "メーカー" + m,
//                    100.0f * i,
//                    CURRENT_CURRENCY,
//                    unit
//            );
//            Executors.ioThread(()-> app.db.partsDao().insert(e));
        }
    }

    public void onFragmentCreateView(){
        hasProgress.addSource(model, s -> onTextEdited());
        hasProgress.addSource(makerModelList, list -> onDataSourceUpdated());
        hasProgress.addSource(modelList, list -> onResourceUpdated());
        hasProgress.addSource(maker, s -> onTextEdited());
        hasProgress.addSource(name, s -> onTextEdited());
        hasProgress.addSource(unit, s -> onTextEdited());
        hasProgress.addSource(priceString, s -> {
            try {
                price.postValue(Float.parseFloat(s));
                isNotNumeric.setValue(false);
            }catch (NumberFormatException e){
                // "入力してください表示"
                isNotNumeric.setValue(true);
            }
        });
        state.postValue(ViewModelState.ready);
    }

    public void onDataSourceUpdated(){
        updateResource();
    }

    public void onTextEdited() {
        updateResource();
    }

    /**
     * 表示や検証のためのリソースを更新します。
     * タイミング：　
     * 画面入力の更新時
     * データソース(DB等)の更新時
     */
    private void updateResource(){
        final String inputMaker = maker.getValue();
        final List<MakerModelListDto> list = makerModelList.getValue();
        if (null == inputMaker || null == list) return;

        hasProgress.setValue(true);
        List<SingleStringListResource> result = new ArrayList<>();
        for (MakerModelListDto makerModelListDto : list) {
            if (inputMaker.equals(makerModelListDto.maker_name)){
                result.add(new SingleStringListResource(makerModelListDto.model));
            }
        }
        modelList.postValue(result);
    }

    public void onResourceUpdated(){
        validateInputData();
    }

    /**
     * 入力を検証します。
     * タイミング：
     * リソースの更新時
     */
    private void validateInputData(){
        final String inputModel = model.getValue();
        final List<SingleStringListResource> listModels = modelList.getValue();
        boolean isUnique = true;
        if (null != listModels && null != inputModel) {
            for (SingleStringListResource listModel : listModels) {
                if (null != listModel && listModel.value.equals(inputModel)) {
                    //duplicated
                    isUnique = false;
                    break;
                }
            }
        }
        isDuplicate.setValue(!isUnique);
        isValidCoreInfo.setValue(Parts.validateMaker(maker.getValue()) && Parts.validateModel(model.getValue()) && isUnique);
        isValidPartsName.setValue(Parts.validateName(name.getValue()));
        float aPrice = (price.getValue() == null ? 0 : price.getValue());
        boolean aIsNotNumeric = isNotNumeric.getValue() != null && !isNotNumeric.getValue();
        isValidPriceInfo.setValue(aIsNotNumeric && Parts.validateUnit(unit.getValue()) && Parts.validateValue(aPrice, CURRENT_CURRENCY));
        onValidUpdated();
    }

    private void onValidUpdated(){
        hasProgress.setValue(false);
    }

    public void onSaveClicked() {
        boolean coreInfo = isValidCoreInfo.getValue() != null && isValidCoreInfo.getValue();
        boolean partsName = isValidPartsName.getValue() != null && isValidPartsName.getValue();
        boolean priceInfo = isValidPriceInfo.getValue() != null && isValidPriceInfo.getValue();
        if(coreInfo && partsName && priceInfo){
            float aPrice = price.getValue() == null ? 0 : price.getValue();
            Executors.ioThread(()->{
                service.registerNewParts(new PartsRegisterDto()
                        .setMaker(maker.getValue())
                        .setModel(model.getValue())
                        .setName(name.getValue())
                        .setUnit(unit.getValue())
                        .setValue(aPrice)
                        .setCurrency(CURRENT_CURRENCY)
                );
            });
//            String s = "maker=" + maker.getValue() + "\n" +
//                    " model=" + model.getValue() + "\n" +
//                    " name=" + name.getValue() + "\n" +
//                    " price=" + price.getValue() + CURRENT_CURRENCY + "/" + unit.getValue();
//            System.out.println(s);
//        }else {
//            System.out.println("is not valid.");
        }else {
            errorMessage.setValue("error! ");
            state.setValue(ViewModelState.error);
        }
    }

    public void outState(List<PartsEntity> partsEntity){
        if (null != partsEntity) {
            for (PartsEntity entity : partsEntity) {
                System.out.println(
                        "id:" + entity.id + "\n" +
                                "ver:" + entity.version + "\n" +
                                "maker:" + entity.maker + "\n" +
                                "model:" + entity.model + "\n" +
                                "name:" + entity.name + "\n" +
                                "unit:" + entity.unit + "\n" +
                                "price:" + entity.price_value + " " + entity.price_currency + "\n");
            }
        }else {
            System.out.println("partsEntityList is null");
        }
    }
}
