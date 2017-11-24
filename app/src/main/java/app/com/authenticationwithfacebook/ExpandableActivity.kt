package app.com.authenticationwithfacebook

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.expandable_activity.*

/**
 * Created by leidycarolinazuluagabastidas on 23/11/17.
 */
class ExpandableActivity : AppCompatActivity(){


    private var expandableListDetail: HashMap<String, List<String>> = HashMap()
    private var expandableListTitle: List<String> = ArrayList()
    private var expandableListAdapter: CustomExpandableListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.expandable_activity)


        val expandableListDataPump = ExpandableListDataPump()
        expandableListDetail = expandableListDataPump.getData()
        expandableListTitle = ArrayList<String>(expandableListDetail.keys)
        expandableListAdapter = CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail)
        expandableListView.setAdapter(expandableListAdapter)

        expandableListView.setOnGroupExpandListener { groupPosition ->
            Toast.makeText(applicationContext,
                    expandableListTitle[groupPosition] + " List Expanded.",
                    Toast.LENGTH_SHORT).show()
        }

        expandableListView.setOnGroupCollapseListener { groupPosition ->
            Toast.makeText(applicationContext,
                    expandableListTitle[groupPosition] + " List Collapsed.",
                    Toast.LENGTH_SHORT).show()
        }


        expandableListView.setOnChildClickListener { parent, view, groupPosition, childPosition, long ->
            Toast.makeText(applicationContext,
                    expandableListTitle[groupPosition] + ": " +
                            expandableListDetail[expandableListTitle[groupPosition]]!![childPosition],
                    Toast.LENGTH_SHORT).show()
            false
        }
    }
}