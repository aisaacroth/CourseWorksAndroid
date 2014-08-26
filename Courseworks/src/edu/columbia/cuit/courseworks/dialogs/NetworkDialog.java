package edu.columbia.cuit.courseworks.dialogs;

import edu.columbia.cuit.courseworks.R;
import android.app.*;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Dialog message that displays any network errors.
 * 
 * @author Alexander Roth
 * @date 2014-08-25
 */
public class NetworkDialog extends DialogFragment {

    public interface NetworkDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
    }

    public NetworkDialogListener listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (NetworkDialogListener) activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new Builder(getActivity());
        builder.setTitle(R.string.network_error);
        builder.setMessage("It appears that there was a network error."
                + " Please try again.");
        builder.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onDialogPositiveClick(NetworkDialog.this);
                    }
                });
        return builder.create();
    }

}
