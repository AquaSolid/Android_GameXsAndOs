package com.example.filip.gamexsandos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by filip on 20.09.2015.
 */
public class FragmentDialogBox extends DialogFragment {
    android.support.v7.app.AlertDialog playerDialog;
    int inputSelection = -1;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // SOMEHOW RETURN A COUNTER, counter%2==GAMEMODE
        setCancelable(false);
        //playerDialog = inflater.inflate(R.layout.fragment_dialog, container, false);
        final CharSequence[] items = {"X", "O"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Who goes first?");
        builder.setSingleChoiceItems(items, inputSelection, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                switch (item) {
                    case 0:
                        Toast.makeText(getActivity(), "Player X goes first.", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(getActivity(), "Player O goes first.", Toast.LENGTH_SHORT).show();
                        break;
                }
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        return dialog;
    }
}
