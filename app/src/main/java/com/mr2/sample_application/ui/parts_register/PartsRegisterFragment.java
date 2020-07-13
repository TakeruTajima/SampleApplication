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

import com.google.android.material.snackbar.Snackbar;
import com.mr2.sample_app_infra.ui_resource.SingleStringListResource;
import com.mr2.sample_application.R;
import com.mr2.sample_application.databinding.FragmentPartsRegisterBinding;
import com.mr2.sample_application.ui.live_dialog.LiveDialogFragment;

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
                int _id = PartsRegisterFragmentArgs.fromBundle(requireArguments()).getPartsId();
                vm.onSaveClicked();
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
        vm.makerModelList.observe(getViewLifecycleOwner(), makerModelList ->{});
        vm.hasProgress.observe(getViewLifecycleOwner(), aBoolean -> {});
//        vm.maker.observe(getViewLifecycleOwner(), maker -> vm.onTextEdited());
//        vm.model.observe(getViewLifecycleOwner(), model -> vm.onTextEdited());
//        vm.name.observe(getViewLifecycleOwner(), name -> vm.onTextEdited());
//        vm.unit.observe(getViewLifecycleOwner(), unit -> vm.onTextEdited());
//        vm.price.observe(getViewLifecycleOwner(), price -> vm.onTextEdited());
        vm.state.observe(getViewLifecycleOwner(), state -> {
            switch (state) {
                case loading:
                    break;
                case ready:
                    break;
                case cation:
                    break;
                case error:
                    String message = vm.errorMessage.getValue() == null ? "no message" : vm.errorMessage.getValue();
                    Snackbar.make(binding.partsRegisterProgress, message, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    vm.state.postValue(ViewModelState.ready);
                    break;
                default:
                    throw new UnsupportedOperationException("リアクションが実装されていないViewModelStateです");
            }
        });

        binding.partsRegisterEditAutoMaker.setThreshold(1);
        binding.partsRegisterEditAutoMaker.setOnFocusChangeListener(this::showDropDown);
        binding.partsRegisterEditAutoModel.setThreshold(10);
//        binding.partsRegisterEditAutoModel.setOnFocusChangeListener(this::showDropDown);
        binding.partsRegisterEditAutoUnit.setThreshold(1);
        binding.partsRegisterEditAutoUnit.setOnFocusChangeListener(this::showDropDown);
        vm.onFragmentCreateView();
        return binding.getRoot();
    }

    private void showDropDown(View v, boolean hasFocus){
        ((AutoCompleteTextView)v).showDropDown();
    }

    @BindingAdapter({"autoCompleteAdapter"})
    public static void autoCompleteAdapter(AutoCompleteTextView textView, List<SingleStringListResource> list){
        String[] con = new String[null == list ? 0 : list.size()];
        for (int i = 0; i < con.length; i++) {
            con[i] = list.get(i).value;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(textView.getContext(), android.R.layout.simple_list_item_1, con);
        textView.setAdapter(adapter);
    }
}
