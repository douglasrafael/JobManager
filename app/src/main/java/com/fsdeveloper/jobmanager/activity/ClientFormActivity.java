package com.fsdeveloper.jobmanager.activity;

import android.animation.LayoutTransition;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.fsdeveloper.jobmanager.R;
import com.fsdeveloper.jobmanager.bean.Client;
import com.fsdeveloper.jobmanager.bean.Phone;
import com.fsdeveloper.jobmanager.bean.PhoneType;
import com.fsdeveloper.jobmanager.exception.ConnectionException;
import com.fsdeveloper.jobmanager.exception.JobManagerException;
import com.fsdeveloper.jobmanager.fragments.GenericDialogFragment;
import com.fsdeveloper.jobmanager.manager.Manager;
import com.fsdeveloper.jobmanager.tool.MyValidate;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity the form client.
 *
 * @author Created by Douglas Rafael
 * @version 1.0
 */
public class ClientFormActivity extends AppCompatActivity implements View.OnClickListener, GenericDialogFragment.OnClickDialogListener {
    public static final int REQUEST_CLIENT = 1;
    public static final String RESULT_CLIENT = "client";
    public final int DIALOG_HAS_CHANGE = 1;
    public static final int REQUEST_CLIENT_UPDATE = 1;

    private EditText textName, textEmail, textAddress, textPhone;
    private Button btAddClient;
    private ImageButton btNewPhone;
    private ImageButton btRemovePhone;
    private AppCompatSpinner spinnerTypesPhone;
    private ArrayAdapter<CharSequence> adapterTypesPhone;
    private LinearLayout containerPhone;
    private ScrollView scrollView;
    private MyValidate validate;
    private Client client, clientEdit;
    private Manager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textName = (EditText) findViewById(R.id.edt_name);
        textEmail = (EditText) findViewById(R.id.edt_email);
        textAddress = (EditText) findViewById(R.id.edt_address);
        textPhone = (EditText) findViewById(R.id.edt_phone);
        spinnerTypesPhone = (AppCompatSpinner) findViewById(R.id.spinner_type_phone);
        containerPhone = (LinearLayout) findViewById(R.id.box_phone);
        scrollView = (ScrollView) findViewById(R.id.scrollViewClientForm);


        // Add event to the buttons
        btAddClient = (Button) findViewById(R.id.bt_add_client);
        btNewPhone = (ImageButton) findViewById(R.id.bt_new_phone);
        btRemovePhone = (ImageButton) findViewById(R.id.bt_remove_phone);
        btAddClient.setOnClickListener(this);
        btNewPhone.setOnClickListener(this);
        btRemovePhone.setOnClickListener(this);

        // Instance the validation
        validate = new MyValidate(this);

        /**
         * Get the client of Intent that called this Activity.
         *
         * If the client has id, form will editing.
         * If the client is null, form will register.
         */
        clientEdit = (Client) getIntent().getSerializableExtra(RESULT_CLIENT);
        if (isEdit() && clientEdit.getId() > 0) {
            getSupportActionBar().setTitle(getResources().getString(R.string.title_client_form_update));

            // Fill form
            fillForm(clientEdit);
        } else {
            client = null;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Set transition in container
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (containerPhone.getLayoutTransition() == null)
                containerPhone.setLayoutTransition(new LayoutTransition());
        }
    }

    /**
     * Event the button back of the bottom bar.
     */
    @Override
    public void onBackPressed() {
        // Checks for client to be edited (form in edit mode)
        if (hasChanged()) {
            // Open dialog back confirm
            GenericDialogFragment dialogHasChange = GenericDialogFragment.newDialog(DIALOG_HAS_CHANGE,
                    R.string.back_confirm, new int[]{android.R.string.ok, android.R.string.cancel}, null);
            dialogHasChange.show(getSupportFragmentManager());
        } else {
            sendClient();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.form_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_discard_form:
                client = null; // Discard changes
                sendClient();

                return true;
            case android.R.id.home:
                // The button back in top bar
                if (hasChanged()) {
                    // Open dialog back confirm
                    GenericDialogFragment dialogHasChange = GenericDialogFragment.newDialog(DIALOG_HAS_CHANGE,
                            R.string.back_confirm, new int[]{android.R.string.ok, android.R.string.cancel}, null);
                    dialogHasChange.show(getSupportFragmentManager());
                } else {
                    // Send to the activity that called and closes the screen
                    sendClient();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_add_client:
                if (isEdit()) {
                    if (validateForm()) {
                        client = getClientOfForm();
                        updateClient(client);
                    }
                } else {
                    // Creating
                    if (validateForm()) {
                        client = getClientOfForm();
                        sendClient();
                    }
                }
                break;
            case R.id.bt_new_phone:
                addComponentsPhone();
                break;
            case R.id.bt_remove_phone:
                removePhone(view);
                break;
        }
    }

    @Override
    public void onClickDialog(int id, int button) {
        if (id == DIALOG_HAS_CHANGE) {
            if (button == DialogInterface.BUTTON_POSITIVE) {
                sendClient();
            }
        }
    }

    /**
     * Update the client.
     *
     * @param client The client
     */
    private void updateClient(Client client) {
        if (client != null) {
            try {
                manager = new Manager(ClientFormActivity.this);
                if (manager.updateClient(client)) {
                    Toast.makeText(this, getResources().getString(R.string.success_edit_client), Toast.LENGTH_SHORT).show();
                    sendClient();
                } else {
                    Toast.makeText(this, getResources().getString(R.string.error_edit_client), Toast.LENGTH_SHORT).show();
                }
            } catch (JobManagerException e) {
                Toast.makeText(this, getResources().getString(R.string.error_system), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } catch (ConnectionException e) {
                Toast.makeText(this, getResources().getString(R.string.error_bd), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    /**
     * Add the components teh phone (EditText, Spinner and ImageButton) in layout
     */
    private View addComponentsPhone() {
        LayoutInflater inflater = getLayoutInflater();
        final View rowPhone = inflater.inflate(R.layout.row_phone, null);

        // Add in layout
        containerPhone.addView(rowPhone);

        // Down the ScrollView
        scrollView.post(new Runnable() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);

                rowPhone.requestFocus();
            }
        });

        return rowPhone;
    }

    /**
     * Remove row the phone.
     *
     * @param view The view content row_phone
     */
    public void removePhone(View view) {
        for (int i = 0; i < containerPhone.getChildCount(); i++) {
            View view_current = containerPhone.getChildAt(i);
            if (view_current == view.getParent()) {
                containerPhone.removeView((View) view.getParent());
                if (containerPhone.getChildCount() == 0) {
                    addComponentsPhone();
                }
            }
        }
    }

    /**
     * Returns phone list entered by the user.
     *
     * @return The listPhones
     */
    public List<Phone> getListPhone() {
        List<Phone> listPhones = new ArrayList<>();

        EditText tempEditTextPhone;
        AppCompatSpinner tempSpinnerTypePhone;

        for (int i = 0; i < containerPhone.getChildCount(); i++) {
            View v = containerPhone.getChildAt(i);

            tempEditTextPhone = (EditText) v.findViewById(R.id.edt_phone);
            tempSpinnerTypePhone = (AppCompatSpinner) v.findViewById(R.id.spinner_type_phone);

            String phone_number = String.valueOf(tempEditTextPhone.getText());
            if (phone_number.length() > 0) {
                PhoneType type = new PhoneType(((int) tempSpinnerTypePhone.getSelectedItemPosition()) + 1, tempSpinnerTypePhone.getSelectedItem().toString());
                Phone phone = new Phone(String.valueOf(tempEditTextPhone.getText()), type);

                listPhones.add(phone);
            }
        }

        return listPhones;
    }

    /**
     * Get the input data and assemble client object.
     *
     * @return The client
     */
    private Client getClientOfForm() {
        Client c = new Client();
        c.setId(isEdit() ? clientEdit.getId() : 0);
        c.setName(String.valueOf(textName.getText()));
        c.setEmail(String.valueOf(textEmail.getText()));
        c.setAddress(String.valueOf(textAddress.getText()));
        c.setPhoneList(getListPhone());

        c.setUser_id(1); // TODO GET of session

        return c;
    }

    /**
     * Send client to the activity that called.
     */
    private void sendClient() {
        Intent intent = new Intent();
        intent.putExtra(RESULT_CLIENT, client);

        if (client != null) {
            setResult(RESULT_OK, intent);
        } else {
            setResult(RESULT_CANCELED, intent);
        }
        // Close Activity
        finish();
    }

    /**
     * Validation the form.
     *
     * @return True if is valid or False otherwise.
     */
    private boolean validateForm() {
        if (validate.isEmpty(textName, R.string.validate_name)) {
            return false;
        } else if (!validate.min(textName, 2, R.string.validate_name_min)) {
            return false;
        } else if (String.valueOf(textEmail.getText()).trim().length() > 0) {
            if (!validate.validEmail(textEmail, R.string.validate_email))
                return false;
        }

        return true;
    }


    /**
     * If clientEdit is different from null it is because the form is in edit mode
     *
     * @returnTrue it is in edit mode and False otherwise
     */
    private boolean isEdit() {
        return clientEdit != null;
    }

    /**
     * Checks whether there were changes in form
     *
     * @return True if yes and False otherwise
     */
    private boolean hasChanged() {
        if (isEdit()) {
            return !(getClientOfForm().equals(clientEdit));
        }
        if (textName.getText().length() > 0 || textEmail.getText().length() > 0
                || textAddress.getText().length() > 0 || getListPhone().size() > 0)
            return true;

        return false;
    }

    /**
     * Fill form teh client.
     *
     * @param c The client
     */
    private void fillForm(final Client c) {
        if (c != null) {
            textName.setText(c.getName());
            textEmail.setText(c.getEmail());
            textAddress.setText(c.getAddress());
            int totalPhones = c.getPhoneList().size();

            if (totalPhones > 0) {
                textPhone.setText(c.getPhoneList().get(0).getNumber());
                spinnerTypesPhone.setSelection(c.getPhoneList().get(0).getType().getId() - 1);

                for (int i = 1; i < totalPhones; i++) {
                    View v = addComponentsPhone();
                    EditText phone = (EditText) v.findViewById(R.id.edt_phone);
                    final AppCompatSpinner type = (AppCompatSpinner) v.findViewById(R.id.spinner_type_phone);

                    phone.setText(c.getPhoneList().get(i).getNumber());

                    final int position = i;
                    type.post(new Runnable() {
                        @Override
                        public void run() {
                            type.setSelection(c.getPhoneList().get(position).getType().getId() - 1);
                        }
                    });
                }
            }

            btAddClient.setText(getResources().getString(R.string.update));
        }
    }

}
