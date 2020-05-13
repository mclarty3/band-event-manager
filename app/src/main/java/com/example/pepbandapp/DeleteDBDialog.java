package com.example.pepbandapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

public class DeleteDBDialog extends DialogFragment {

    DeleteDBDialogListener listener;
    String dataType; // Should be 'event' or 'member'

    interface DeleteDBDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    public DeleteDBDialog(String data)
    {
        dataType = data;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure you want to erase all " + dataType + (" data? This action " +
                "is irreversible."))
                .setPositiveButton("Erase " + dataType + " data", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (dataType.equals("member"))
                        {
                            MainActivity.memberDB.DropTable();
                            MainActivity.memberList = new ArrayList<>();
                            for (Event event: MainActivity.eventList)
                            {
                                event.memberAttendance = new ArrayList<>();
                            }
                        }
                        else if (dataType.equals("event"))
                        {
                            MainActivity.eventsDB.DropTable();
                            MainActivity.eventList = new ArrayList<>();
                            MainActivity.currentlyDisplayedEvent = null;
                        }
                        else if (dataType.equals("attendance"))
                        {
                            for (Event event: MainActivity.eventList)
                            {
                                event.EraseMemberAttendance();
                            }
                        }
                        listener.onDialogPositiveClick(DeleteDBDialog.this);
                    }
                })
                .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        listener.onDialogNegativeClick(DeleteDBDialog.this);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (DeleteDBDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString()
                    + " must implement NoticeDialogListener");
        }
    }
}
