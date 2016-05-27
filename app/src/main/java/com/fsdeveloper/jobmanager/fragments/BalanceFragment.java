package com.fsdeveloper.jobmanager.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TextView;

import com.fsdeveloper.jobmanager.R;
import com.fsdeveloper.jobmanager.bean.Balance;
import com.fsdeveloper.jobmanager.bean.User;
import com.fsdeveloper.jobmanager.exception.ConnectionException;
import com.fsdeveloper.jobmanager.exception.JobManagerException;
import com.fsdeveloper.jobmanager.manager.Manager;
import com.fsdeveloper.jobmanager.tool.MyDataTime;
import com.fsdeveloper.jobmanager.tool.MyDatePicker;
import com.fsdeveloper.jobmanager.tool.MyStringsTool;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * Fragment the balance.
 *
 * @author Created by Douglas Rafael on 22/05/2016.
 * @version 1.0
 */
public class BalanceFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private Context context;
    private TextView textInputValue, textOutputValue, textProfitValue, textDataFilter, textAverageInput, textAverageOutput, textAverageProfit;
    private SwitchCompat switchIncludeFinalized;
    private String dateFilterStart;
    private String dateFilterEnd;
    private String dateCurrent;
    private int countOpenDialog;
    private boolean getBalanceOfDataPicker;
    private Balance balance;
    private int[] dateStart, dateEnd;
    private Toolbar toolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        setHasOptionsMenu(true);

        balance = new Balance();
        dateFilterStart = "";
        dateFilterEnd = "";
        countOpenDialog = 0;
        getBalanceOfDataPicker = false;

        dateStart = new int[3];
        dateEnd = new int[3];
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.context = inflater.getContext();
        View view = inflater.inflate(R.layout.balance_fragment, container, false);

        textInputValue = (TextView) view.findViewById(R.id.text_value_input_balance);
        textOutputValue = (TextView) view.findViewById(R.id.text_value_output_balance);
        textProfitValue = (TextView) view.findViewById(R.id.text_value_total_balance);
        textDataFilter = (TextView) view.findViewById(R.id.text_data_filter_balance);
        textAverageInput = (TextView) view.findViewById(R.id.text_average_input_balance);
        textAverageOutput = (TextView) view.findViewById(R.id.text_average_output_balance);
        textAverageProfit = (TextView) view.findViewById(R.id.text_average_profit_balance);
        switchIncludeFinalized = (SwitchCompat) view.findViewById(R.id.switch_include_not_finalized_balance);

        FloatingActionButton b = (FloatingActionButton) getActivity().findViewById(R.id.fab_change_date_balance);
        b.setOnClickListener(this);

        // Add event in the SwitchCompat
        switchIncludeFinalized.setOnCheckedChangeListener(this);

        /**
         * Getting date current of teh system
         */
        dateEnd = MyDataTime.dateToArray(MyDataTime.getDataTime(context.getResources().getString(R.string.date_balance)));
        dateCurrent = MyDataTime.getDataTime(dateEnd[0], dateEnd[1] - 1, dateEnd[2], 0, 0, context.getResources().getString(R.string.date_balance));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getCurrentBalance(switchIncludeFinalized.isChecked());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.share_menu, menu); //define o arquivo de menu
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_share_balance:
                shareBalance();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_change_date_balance:
                showDatePickerDialog();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (compoundButton.getId() == R.id.switch_include_not_finalized_balance) {
            getCurrentBalance(switchIncludeFinalized.isChecked());
        }
    }

    /**
     * Show data picker.
     */
    public void showDatePickerDialog() {
        final DialogFragment newFragment = new MyDatePicker() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                super.onDateSet(datePicker, year, month, day);
                countOpenDialog += 1;

                if (countOpenDialog == 2) {
                    countOpenDialog = 0;
                    dateFilterEnd = MyDataTime.getDataTime(year, month, day, 0, 0, context.getResources().getString(R.string.date_balance));
                    balance.setDateEnd(dateFilterEnd);
                    dateEnd = MyDataTime.dateToArray(dateFilterEnd);

                    getBalanceOfDataPicker = true;

                    // Mount the balance according to the selected dates
                    getCurrentBalance(switchIncludeFinalized.isChecked());
                } else {
                    dateFilterStart = MyDataTime.getDataTime(year, month, day, 0, 0, context.getResources().getString(R.string.date_balance));
                    balance.setDateStart(dateFilterStart);
                    dateStart = MyDataTime.dateToArray(dateFilterStart);

                    // Open dialog date start
                    showDatePickerDialog();
                }
            }
        };
        newFragment.show(getFragmentManager(), "datePicker");
    }

    /**
     * Mount the balance according to the selected dates
     */
    public void getCurrentBalance(boolean includeNotFinalized) {
        try {
            Manager manager = new Manager(context);

            if (!getBalanceOfDataPicker) {
                User user = manager.getUserById(1); // TODO Trocar pelo id do usuario logado

                dateStart = MyDataTime.dateToArray(user.getCreated_at());
                dateFilterStart = MyDataTime.getDataTime(dateStart[0], dateStart[1] - 1, dateStart[2], 0, 0, context.getResources().getString(R.string.date_balance));

                dateEnd = MyDataTime.dateToArray(MyDataTime.getDataTime(context.getResources().getString(R.string.date_balance)));
                dateFilterEnd = dateCurrent; // Current date
            }

            balance = manager.getBalanceJobs(dateFilterStart + " 00:00:00", dateFilterEnd + " 23:59:59", 1, includeNotFinalized); // TODO Trocar pelo id do usuario logado

            fillFields(balance);
        } catch (JobManagerException | ConnectionException e) {
            e.printStackTrace();
        }
    }

    private void fillFields(Balance b) {
        if (haveBalance()) {
            NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
            StringBuilder shareHtmlText = new StringBuilder();

            String dateFilter;

            if (dateFilterStart.equals(dateFilterEnd) && dateCurrent.equals(dateFilterEnd)) {
                dateFilter = context.getResources().getString(R.string.today_balance);
            } else if (dateFilterStart.equals(dateFilterEnd) && !dateCurrent.equals(dateFilterEnd)) {
                dateFilter = MyDataTime.getDataTime(dateStart[0], dateStart[1] - 1, dateStart[2], 0, 0, context.getResources().getString(R.string.date_balance_view));
            } else {
                dateFilter = context.getResources().getString(R.string.date_filter_balance,
                        MyDataTime.getDataTime(dateStart[0], dateStart[1] - 1, dateStart[2], 0, 0, context.getResources().getString(R.string.date_balance_view)),
                        MyDataTime.getDataTime(dateEnd[0], dateEnd[1] - 1, dateEnd[2], 0, 0, context.getResources().getString(R.string.date_balance_view)));
            }

            textDataFilter.setText(dateFilter);

            textInputValue.setText(String.format("%.2f", b.getInputValue()));
            textOutputValue.setText(String.format("%.2f", b.getOutputValue()));
            textProfitValue.setText(String.format("%.2f", b.getTotalValue()));

            // Average
            textAverageInput.setText(numberFormat.format(new BigDecimal(balance.getAverageInput())));
            textAverageOutput.setText(numberFormat.format(new BigDecimal(balance.getAverageOutput())));
            textAverageProfit.setText(numberFormat.format(new BigDecimal(balance.getAverageProfit())));
        }
    }

    /**
     * Check if exist balance.
     *
     * @return
     */
    private boolean haveBalance() {
        if (balance == null) {
            return false;
        } else {
            if ((balance.getDateStart() == null || balance.getDateStart().isEmpty()) || (balance.getDateEnd() == null || balance.getDateEnd().isEmpty()) ||
                    (balance.getInputValue() == null || balance.getOutputValue() == null || balance.getTotalValue() == null)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Share the balance
     */
    private void shareBalance() {
        if (haveBalance()) {
            NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
            StringBuilder shareHtmlText = new StringBuilder();

            // Setting the title
            shareHtmlText.append("<h2>" + context.getResources().getString(R.string.balance) + ", ");
            if (dateFilterStart.equals(dateFilterEnd)) {
                shareHtmlText.append(MyDataTime.getDataTime(dateStart[0], dateStart[1] - 1, dateStart[2], 0, 0, context.getResources().getString(R.string.date_balance_view)) + "</h2>");
            } else {
                shareHtmlText.append(context.getResources().getString(R.string.date_filter_balance,
                        MyDataTime.getDataTime(dateStart[0], dateStart[1] - 1, dateStart[2], 0, 0, context.getResources().getString(R.string.date_balance_view)),
                        MyDataTime.getDataTime(dateEnd[0], dateEnd[1] - 1, dateEnd[2], 0, 0, context.getResources().getString(R.string.date_balance_view))));
            }
            shareHtmlText.append("</h2>");

            // Setting the input value
            shareHtmlText.append(MyStringsTool.setStyleSimpleBox(getResources().getString(R.string.input_balance), numberFormat.format(new BigDecimal(balance.getInputValue()))));

            // Setting the output
            shareHtmlText.append(MyStringsTool.setStyleSimpleBox(getResources().getString(R.string.output_balance), numberFormat.format(new BigDecimal(balance.getOutputValue()))));

            // Setting the total
            shareHtmlText.append(MyStringsTool.setStyleSimpleBox(getResources().getString(R.string.total_balance), numberFormat.format(new BigDecimal(balance.getTotalValue()))));

            // Setting the average input
            shareHtmlText.append(MyStringsTool.setStyleSimpleBox(getResources().getString(R.string.average_input_balance), numberFormat.format(new BigDecimal(balance.getAverageInput()))));

            // Setting the average output
            shareHtmlText.append(MyStringsTool.setStyleSimpleBox(getResources().getString(R.string.average_output_balance), numberFormat.format(new BigDecimal(balance.getAverageOutput()))));

            // Setting the average profit
            shareHtmlText.append(MyStringsTool.setStyleSimpleBox(getResources().getString(R.string.average_profit_balance), numberFormat.format(new BigDecimal(balance.getAverageProfit()))));

            Intent shareIntent = ShareCompat.IntentBuilder.from(this.getActivity())
                    .setType("text/html")
                    .setHtmlText(String.valueOf(shareHtmlText))
                    .setSubject(getResources().getString(R.string.app_name) + " - " + getResources().getString(R.string.balance))
                    .getIntent();

            if (shareIntent.resolveActivity(context.getPackageManager()) != null) {
                startActivity(Intent.createChooser(shareIntent, getResources().getString(R.string.share_balance)));
            } else {
                GenericDialogFragment alertDialog = GenericDialogFragment.newDialog(1, R.string.no_support_functionality,
                        new int[]{android.R.string.ok}, null);
                alertDialog.show(getFragmentManager());
            }
        }
    }

}
