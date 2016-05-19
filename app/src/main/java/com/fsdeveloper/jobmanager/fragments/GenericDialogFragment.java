package com.fsdeveloper.jobmanager.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;

import com.fsdeveloper.jobmanager.R;

/**
 * Mount a Generic AlertDialog. Receiving the title of the titles of the buttons...
 *
 * @author Created by Douglas Rafael on 18/05/2016.
 * @version 1.0
 */
public class GenericDialogFragment extends DialogFragment {
    public static final int REQUEST_DIALOG = 2;
    private static final String DIALOG_TAG = "SimpleDialog";
    private static final String ID = "id";
    private static final String MESSAGE = "message";
    private static final String TITLE = "title";
    private static final String BUTTONS = "buttons";
    private static final String ICON = "icon";

    private int mDialogId;

    /**
     * Initializes the AlertDialog.
     *
     * @param id      The id
     * @param title   The id string of title
     * @param message The id string of message
     * @param buttons The buttons
     * @return The GenericDialogFragment
     */
    public static GenericDialogFragment newDialog(int id, int title, int message, int icon, int[] buttons) {
        Bundle bundle = new Bundle();
        bundle.putInt(ID, id);
        bundle.putInt(TITLE, title);
        bundle.putInt(MESSAGE, message);
        bundle.putInt(ICON, icon);
        bundle.putIntArray(BUTTONS, buttons);

        GenericDialogFragment dialogFragment = new GenericDialogFragment();
        dialogFragment.setArguments(bundle);

        return dialogFragment;
    }

    public static GenericDialogFragment newDialog(int id, int message, int[] buttons) {
        Bundle bundle = new Bundle();
        bundle.putInt(ID, id);
        bundle.putInt(MESSAGE, message);
        bundle.putIntArray(BUTTONS, buttons);

        GenericDialogFragment dialogFragment = new GenericDialogFragment();
        dialogFragment.setArguments(bundle);

        return dialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDialogId = getArguments().getInt(ID);
        int[] buttons = getArguments().getIntArray(BUTTONS);

        AlertDialog.Builder alertDiBuilder = new AlertDialog.Builder(getActivity());
        if (getArguments().getInt(TITLE) != 0) {
            alertDiBuilder.setTitle(getArguments().getInt(TITLE));
        }
        if (getArguments().getInt(ICON) != 0) {
            alertDiBuilder.setIcon(getArguments().getInt(ICON));
        }

        alertDiBuilder.setMessage(getArguments().getInt(MESSAGE));

        switch (buttons.length) {
            case 2:
                alertDiBuilder.setNegativeButton(buttons[1], new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (getTargetRequestCode() != 0)
                            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, null);
                    }
                });
            case 1:
                alertDiBuilder.setPositiveButton(buttons[0], new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (getTargetRequestCode() != 0)
                            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, null);
                    }
                });
        }

        return alertDiBuilder.create();
    }
//
//    @Override
//    public void onClick(DialogInterface dialogInterface, int i) {
//        Activity activity = getActivity();
//        if (activity instanceof OnClickDialogListener) {
//            OnClickDialogListener listener = (OnClickDialogListener) activity;
//            listener.onClickDialog(mDialogId, i);
//        }
//    }


//    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        // Verify that the host activity implements the callback interface
//        try {
//            // Instantiate the NoticeDialogListener so we can send events to the host
//            mListener = (OnClickDialogListener) activity;
//        } catch (ClassCastException e) {
//            // The activity doesn't implement the interface, throw exception
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnClickDialogListener");
//        }
//    }

    /**
     * Show Dialog
     *
     * @param supportFragmentManager The FragmentManager
     */
    public void show(FragmentManager supportFragmentManager) {
        Fragment dialogFragment = supportFragmentManager.findFragmentByTag(DIALOG_TAG);
        if (dialogFragment == null) {
            this.show(supportFragmentManager, DIALOG_TAG);
        }
    }

//    public interface OnClickDialogListener {
//        void onClickDialog(DialogFragment dialogFragment, int button);
//    }
}
