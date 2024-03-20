package cs445.budgetapp.ui.budget;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.database.annotations.Nullable;

import java.text.DateFormat;
import java.util.Calendar;

import cs445.budgetapp.MyApplication;
import cs445.budgetapp.R;


public class DatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    private Button editDate;

    public void setField(Button button) {
        this.editDate = button;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar mCalendar = Calendar.getInstance();
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int dayOfMonth = mCalendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this::onDateSet, year, month, dayOfMonth);

    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        if (editDate.getId() == R.id.edit_budget_end_date){
            MyApplication.setEndDay(dayOfMonth);
            MyApplication.setEndMonth(month);
            MyApplication.setEndYear(year);

        } else if (editDate.getId() == R.id.edit_budget_start_date) {
            MyApplication.setStartDay(dayOfMonth);
            MyApplication.setStartYear(year);
            MyApplication.setStartMonth(month);
        } else{
            Log.v("Error", "Invalid button selection");
        }
        String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(mCalendar.getTime());
        editDate.setText(selectedDate);
    }
}
