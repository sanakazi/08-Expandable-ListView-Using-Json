package com.example.foodcust.adapter;

import java.util.ArrayList;

import com.example.foodcust.R;
import com.example.foodcust.SelectCusineByRestaurantId;

import com.example.foodcust.models.CusineSubMenuItemModel;
import com.example.foodcust.models.CusineSubmenuModel;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/*public class CusineSubmenuAdapter extends ArrayAdapter<CusineSubmenuModel>{
	private final Activity context;
	private ArrayList<CusineSubmenuModel> names;
	TextView lblListHeader;
	
	
	public CusineSubmenuAdapter (Activity context, ArrayList<CusineSubmenuModel> names )
	{
		super(context, R.layout.cusine_submenu,names);
		this.context=context;
		this.names=names;
		// TODO Auto-generated constructor stub
	}
	
	static class ViewHolder{
		public TextView lblListHeader;
		
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		View rowView=convertView;
		if(rowView==null){
			 LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.cusine_submenu, null, true);
			            	holder = new ViewHolder();
				            holder.lblListHeader = (TextView) rowView.findViewById(R.id.lblListHeader);
				            
				            rowView.setTag(holder);
		}else{
			holder=(ViewHolder)rowView.getTag();
		}
	
		CusineSubmenuModel blipVar=names.get(position);
		if(blipVar!=null)
		{
			 holder.lblListHeader.setText(blipVar.getFoodSubMenuName());
			
		//	holder.img1.setImageDrawable(Conf.loadImageFromURL(blipVar.getRestaurentImage()));

		}
		
		return rowView;
		
		
   }
}
*/


public class CusineSubmenuAdapter extends BaseExpandableListAdapter{
	CheckedTextView outer;
	Context context;
	  public LayoutInflater inflater;
	 public ArrayList<CusineSubmenuModel>groups;
      
       
	
      /*************  CustomAdapter Constructor *****************/
      public CusineSubmenuAdapter (Context context, ArrayList<CusineSubmenuModel> groups ) {
           
             /********** Take passed values **********/
    	     this.context = context;
    	        this.groups = groups;
           
      }
 
   

      public Object getChild(int groupPosition, int childPosition) {
          ArrayList<CusineSubMenuItemModel> chList = groups.get(groupPosition).getItems();
          return chList.get(childPosition);
      }



@Override
public long getChildId(int groupPosition, int childPosition) {
    return childPosition;
}




@Override
public View getChildView(int groupPosition, int childPosition,
        boolean isLastChild, View convertView, ViewGroup parent) {

    CusineSubMenuItemModel child = (CusineSubMenuItemModel) getChild(groupPosition, childPosition);
    if (convertView == null) {
        LayoutInflater infalInflater = (LayoutInflater) context
                .getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView = infalInflater.inflate(R.layout.cusine_submenu_inner, null);
    }

  /*  if (imageLoader == null)
        imageLoader = MyApplication.getInstance().getImageLoader();

   
    NetworkImageView iv = (NetworkImageView) convertView
            .findViewById(R.id.flag);*/
    TextView inner = (TextView) convertView.findViewById(R.id.inner);
    inner.setText(child.getFoodItemName().toString());
   // iv.setImageUrl(child.getImage(), imageLoader);

    return convertView;
}




@Override
public int getChildrenCount(int groupPosition) {
    ArrayList<CusineSubMenuItemModel> chList = groups.get(groupPosition).getItems();
    return chList.size();
}



@Override
public Object getGroup(int groupPosition) {
    return groups.get(groupPosition);
}



@Override
public int getGroupCount() {
    return groups.size();
}

public void onGroupCollapsed(int groupPosition) {
    super.onGroupCollapsed(groupPosition);
  }

public void onGroupExpanded(int groupPosition) {
    super.onGroupExpanded(groupPosition);
  }


@Override
public long getGroupId(int groupPosition) {
    return groupPosition;
}

@Override
public View getGroupView(int groupPosition, boolean isExpanded,
        View convertView, ViewGroup parent) {
    CusineSubmenuModel group = (CusineSubmenuModel) getGroup(groupPosition);
    if (convertView == null) {
        LayoutInflater inf = (LayoutInflater) context
                .getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView = inf.inflate(R.layout.cusine_submenu, null);
    }
    TextView outer = (TextView) convertView.findViewById(R.id.outer);
    outer.setText(group.getFoodSubMenuName());
    
    return convertView;
}

@Override
public boolean hasStableIds() {
	// TODO Auto-generated method stub
	return false;
}



@Override
public boolean isChildSelectable(int arg0, int arg1) {
	// TODO Auto-generated method stub
	return false;
}   
    
}