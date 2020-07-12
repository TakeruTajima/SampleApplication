package com.mr2.sample_application.ui.parts_register;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mr2.sample_app_infra.ui_resource.SingleStringListResource;
import com.mr2.sample_app_infra.ui_resource.parts_register.MakerListDto;
import com.mr2.sample_app_infra.ui_resource.parts_register.UnitListDto;
import com.mr2.sample_application.R;
import com.mr2.sample_application.databinding.FragmentPartsRegisterBinding;

import java.util.List;

public class PartsRegisterFragment extends Fragment {
    private PartsRegisterViewModel vm;

    public static PartsRegisterFragment newInstance() {

        Bundle args = new Bundle();

        PartsRegisterFragment fragment = new PartsRegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        vm = new ViewModelProvider
                .AndroidViewModelFactory(requireActivity().getApplication())
                .create(PartsRegisterViewModel.class);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.option, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                getParentFragmentManager().popBackStack();
                break;
            case R.id.menu_save:
                Bundle args = getArguments();
                int _id = //Objects.requireNonNull(args).getInt("partsId");
                PartsRegisterFragmentArgs.fromBundle(requireArguments()).getPartsId();
//                Snackbar.make(getView(), "Replace with your own action: " + _id, Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                vm.onSaveClicked();
                Toast.makeText(getContext(), "保存されました: id=" + _id, Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentPartsRegisterBinding binding;
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_parts_register, container, false);
        binding.setLifecycleOwner(this);
        binding.setVm(vm);
        vm.maker.observe(getViewLifecycleOwner(), maker -> {
            vm.onEdit();
        });
        vm.makerModelList.observe(getViewLifecycleOwner(), makerModelList ->{});
        vm.model.observe(getViewLifecycleOwner(), model -> vm.onEdit());
        vm.name.observe(getViewLifecycleOwner(), name -> vm.onEdit());
        vm.unit.observe(getViewLifecycleOwner(), unit -> vm.onEdit());
        vm.price.observe(getViewLifecycleOwner(), price -> vm.onEdit());
        vm.isValidCoreInfo.observe(getViewLifecycleOwner(), isDone ->
                binding.partsRegisterDone1.setVisibility(isDone ? View.VISIBLE : View.INVISIBLE));

        binding.partsRegisterEditAutoMaker.setThreshold(1);
        binding.partsRegisterEditAutoMaker.setOnFocusChangeListener(this::showDropDown);
        binding.partsRegisterEditAutoModel.setThreshold(1);
        binding.partsRegisterEditAutoModel.setOnFocusChangeListener(this::showDropDown);
        binding.partsRegisterEditAutoUnit.setThreshold(1);
        binding.partsRegisterEditAutoUnit.setOnFocusChangeListener(this::showDropDown);
        return binding.getRoot();
    }

    private void showDropDown(View v, boolean hasFocus){
        ((AutoCompleteTextView)v).showDropDown();
    }

//
//    @BindingAdapter({"suggestUnit"})
//    public static void suggestUnit(AutoCompleteTextView textView, List<UnitListDto> list){
//        String[] con = new String[null == list ? 0 : list.size()];
//        for (int i = 0; i < con.length; i++) {
//            con[i] = list.get(i).name;
//        }
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(textView.getContext(), android.R.layout.simple_list_item_1, con);
//        textView.setAdapter(adapter);
//    }

    @BindingAdapter({"autoCompleteAdapter"})
    public static void autoCompleteAdapter(AutoCompleteTextView textView, List<SingleStringListResource> list){
        String[] con = new String[null == list ? 0 : list.size()];
        for (int i = 0; i < con.length; i++) {
            con[i] = list.get(i).value;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(textView.getContext(), android.R.layout.simple_list_item_1, con);
        textView.setAdapter(adapter);
    }

//    @SuppressWarnings("unchecked")
//    private static ArrayAdapter<String> cast(ListAdapter listAdapter){
//        return (ArrayAdapter<String>) listAdapter;
//    }
}
