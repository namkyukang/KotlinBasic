package com.androidhuman.example.simplecontacts

import com.androidhuman.example.simplecontacts.model.Person

import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText

import io.realm.Realm
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity() {

//    lateinit var tlName: TextInputLayout    //val은 안된다.
//
//    lateinit var etName: EditText
//
//    lateinit var etAddress: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
//
//        tlName = findViewById(R.id.ti_activity_edit_name) as TextInputLayout
//        etName = findViewById(R.id.et_activity_edit_name) as EditText
//        etAddress = findViewById(R.id.et_activity_edit_address) as EditText
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_edit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (R.id.menu_activity_edit_done == item.itemId) {
            applyChanges()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun applyChanges() {
        if (et_activity_edit_name.text.isEmpty()) {
            // TODO: Remove error on content changes
            ti_activity_edit_name.error = getText(R.string.msg_name_cannot_be_empty)
            return
        }

        val realm = Realm.getDefaultInstance()

        // Get next id value for primary key
        val currentMaxId = realm.where(Person::class.java).max(Person.PRIMARY_KEY)
        val nextId: Long?
        if (null == currentMaxId) {
            nextId = 0L
        } else {
            nextId = currentMaxId.toLong() + 1
        }

        realm.beginTransaction()//-----------------------------------------------------------

        val person = realm.createObject(Person::class.java, nextId)
        person.name = et_activity_edit_name.text.toString()
        person.address = et_activity_edit_address.text.toString()

        /* Alternative method:
        Person person = new Person();
        person.setId(nextId);
        person.setName(etName.getText().toString());
        person.setAddress(etAddress.getText().toString());
        realm.copyToRealm(person);
        */

        realm.commitTransaction()//-----------------------------------------------------------실제적으로 데이터에 반영이된다.

        realm.close()

        setResult(RESULT_OK)
        finish()
    }
    companion object {
        //public static final int REQUEST_CODE = 10 kotlin에서는 static이 없다.
        val REQUEST_CODE = 10
    }
}
