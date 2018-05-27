package ru.ssermakov.newrecycler.view.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.util.Objects;

import ru.ssermakov.newrecycler.R;
import ru.ssermakov.newrecycler.view.BeginIllnessActivity;

public class ItemChangeDialogFragment extends android.support.v4.app.DialogFragment {

    public interface EventListener {
        void event(String s);
    }

    EventListener eventListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            eventListener = (EventListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement EventListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        View v = LayoutInflater.from(getActivity()).inflate(R.layout.change_item_content_dialog, null);
        final EditText editText = v.findViewById(R.id.editTextItem);
        if (!BeginIllnessActivity.itemContent.equals("")) {
            editText.setText(BeginIllnessActivity.itemContent);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                .setTitle("Change")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eventListener.event(editText.getText().toString());
                    }
                })
                .setView(v)
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setMessage("Message");
        return builder.create();
    }
}
