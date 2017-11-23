package app.com.authenticationwithfacebook

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView

/**
 * Created by leidycarolinazuluagabastidas on 23/11/17.
 */
class CustomExpandableListAdapter(context: Context, expandableListTitle: List<String>,
                                  expandableListDetail: HashMap<String, List<String>>)
    : BaseExpandableListAdapter() {

    var context: Context = context
    var expandableListTitle: List<String> = expandableListTitle
    var expandableListDetail: HashMap<String, List<String>> = expandableListDetail


    override fun getGroup(p0: Int): Any {
        return this.expandableListTitle.get(p0)
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(p0: Int, p1: Boolean, p2: View?, p3: ViewGroup?): View {
        var listTitle: String = getGroup(p0) as String
        val convertView: View
        convertView = if (p2 == null) {
            val layoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            layoutInflater.inflate(R.layout.list_group, null)
        } else {
            p2
        }
        val listTitleTextView = convertView.findViewById<TextView>(R.id.listTitle)
        listTitleTextView.setTypeface(null, Typeface.BOLD)
        listTitleTextView.text = listTitle
        return convertView
    }

    override fun getChildrenCount(p0: Int): Int {
        return this.expandableListDetail[this.expandableListTitle[p0]]!!.size
    }

    override fun getChild(p0: Int, p1: Int): Any {
        return this.expandableListDetail[this.expandableListTitle[p0]]?.get(p1)!!
    }

    override fun getGroupId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getChildView(p0: Int, p1: Int, p2: Boolean, p3: View?, p4: ViewGroup?): View {
        var expandedListText: String = getChild(p0, p1) as String
        val convertView: View

        convertView = if (p3 == null) {
            val layoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            layoutInflater.inflate(R.layout.list_item, null)
        } else {
            p3
        }

        val expandedListTextView = convertView.findViewById<TextView>(R.id.expandedListItem)
        expandedListTextView.text = expandedListText
        return convertView
    }

    override fun getChildId(p0: Int, p1: Int): Long {
        return p1.toLong()
    }

    override fun getGroupCount(): Int {
        return this.expandableListTitle.size
    }


}