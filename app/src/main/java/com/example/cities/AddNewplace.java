package com.example.cities;


import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.cities.Model.CitiesModel;
import com.example.cities.Utils.DatabaseHandler;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.example.cities.Adapters.Adapter;

import java.util.Objects;

public class AddNewplace extends BottomSheetDialogFragment {

    public static final String TAG = "ActionBottomDialog";
    private EditText newplaceText;
    private Button newplaceSaveButton;

    private DatabaseHandler db;

    public static AddNewplace newInstance(){
        return new AddNewplace();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.new_place, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newplaceText =requireView().findViewById(R.id.newplaceText);
        newplaceSaveButton = getView().findViewById(R.id.newplaceButton);

        boolean isUpdate = false;

        final Bundle bundle = getArguments();
        if(bundle != null){
            isUpdate = true;
            String place = bundle.getString("place");
            newplaceText.setText(place);
            assert place != null;
            if(place.length()>0)
                newplaceSaveButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark));
        }

        db = new DatabaseHandler(getActivity());
        db.openDatabase();

        newplaceText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    newplaceSaveButton.setEnabled(false);
                    newplaceSaveButton.setTextColor(Color.GRAY);
                }
                else{
                    newplaceSaveButton.setEnabled(true);
                    newplaceSaveButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        final boolean finalIsUpdate = isUpdate;
        newplaceSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = newplaceText.getText().toString();
                if(finalIsUpdate){
                    db. updatePlace(bundle.getInt("id"), (text));
                }
                else {
                    CitiesModel place = new CitiesModel();
                    place.setPlace(text);
                    place.setStatus(0);
                    db.insertplace(place);
                }
                dismiss();
            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog){
        Activity activity = getActivity();
        if(activity instanceof DialogCloseListener)
            ((DialogCloseListener)activity).handleDialogClose(dialog);
    }
}
