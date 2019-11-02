package mmf.piskunou.lab3.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import com.example.androiddev3.R;

import androidx.fragment.app.DialogFragment;

public class AboutDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.about_application);

        return builder.create();
    }
}
