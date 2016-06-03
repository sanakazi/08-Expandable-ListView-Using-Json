package com.example.foodcust;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import com.example.foodcust.adapter.CusineSubmenuAdapter;
import com.example.foodcust.models.CusineSubMenuItemModel;
import com.example.foodcust.models.CusineSubmenuModel;
import com.example.foodcust.restaurantservice.HttpRequest;
import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.content.ContentValues;
import android.content.res.Resources;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SelectCusineByRestaurantId extends Activity{
	String RestaurantId,IsActive,FoodMasterId,FoodMenuName,FoodSubMenuName,FoodSubMenuId,HotelId,FoodItemName;
	public boolean isSuccess = false;
    ArrayList<String> arr;
    ExpandableListView lvExp;
   ArrayList<CusineSubmenuModel> arr1;
   ArrayList<CusineSubMenuItemModel> arr1_child;
    CusineSubmenuAdapter adapter;
    
    Resources res;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	   setContentView(R.layout.activity_select_cusine_by_restaurant_id);
		final ActionBar bar= getActionBar();
		  bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	        bar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
	       lvExp = (ExpandableListView) findViewById(R.id.lvExp);
	       arr1 = new ArrayList<CusineSubmenuModel>();
	      
	       res =getResources();
	 AsyncWS task = new AsyncWS();
		task.execute();
		
	   AsyncSubMenu task1 = new AsyncSubMenu();
	   task1.execute();
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.select_cusine_by_restaurant_id, menu);
		return true;
	}
	
	class AsyncWS extends AsyncTask
	{

		@Override
		protected Object doInBackground(Object... params) {
			// TODO Auto-generated method stub
			setListData();

			return null;
		}
		
		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
		
			 for (int i = 0; i < arr.size(); i++) {
				    String  value = arr.get(i);
				    Log.d("Element: " , value); 
				    final ActionBar bar = getActionBar();
			        final int tabCount = bar.getTabCount();
			        final String text = value;
			        bar.addTab(bar.newTab()
			                .setText(text)
			                .setTabListener(new TabListener(value)));
				}
		       
		       
			
		}
	}
	
	
	public void setListData()
	{
		arr = new ArrayList<String>();
		HttpRequest hr = new HttpRequest();
		try {

			ContentValues cv = new ContentValues();
			// vp.onPageSelected(position);
			
			cv.put("RestaurantId", "23");
			
			String res = hr.getDataFromServer(cv, "selectCusineByRestaurantId");
			Log.d("Res" , res);
			isSuccess = true;
		//	return res;
			
			if(isSuccess)
			{
				JSONObject  jsonRootObject = new JSONObject(res);
				JSONArray jsonArray = jsonRootObject.optJSONArray("Table");
				//JSONArray jsonArray = new JSONArray(res);
				Log.d("Json Object", jsonRootObject.toString());
				Log.d("Json Array", jsonArray.toString());
				 for(int i=0; i < jsonArray.length(); i++)
				 {
					 JSONObject jsonObject = jsonArray.getJSONObject(i);
			         Log.d("Json object:", jsonObject.toString());
			         RestaurantId = jsonObject.optString("RestaurantId").toString();
		             Log.d("RestaurantId",RestaurantId.toString());
		             FoodMenuName = jsonObject.optString("FoodMenuName").toString();
		             FoodMasterId = jsonObject.optString("FoodMasterId").toString();
		             IsActive = jsonObject.optString("IsActive").toString();
		             arr.add(FoodMenuName);
				 }
			}
			else
			{
				Toast.makeText(this, "No associated Restaurants", Toast.LENGTH_SHORT).show();
			}
			  
			 
		} 
		catch (SocketTimeoutException e) {
			isSuccess = false;
			e.printStackTrace();
		} catch (SocketException e) {
			isSuccess = false;
			e.printStackTrace();
		} catch (IOException e) {
			isSuccess = false;
			e.printStackTrace();
		} 
		catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		 catch (JSONException e) {e.printStackTrace();}
	}
	
	   /**
     * A TabListener receives event callbacks from the action bar as tabs
     * are deselected, selected, and reselected. 
     **/
    private class TabListener implements ActionBar.TabListener {
        private String str;
        private SelectCuisineFragment mFragment;
        public TabListener(String str) {
            str = str;
        }
 
        public void onTabSelected(Tab tab, FragmentTransaction ft) {
           // ft.add(R.id.fragment_content, mFragment);
        }
 
        public void onTabUnselected(Tab tab, FragmentTransaction ft) {
            ft.remove(mFragment);
        }
 
        public void onTabReselected(Tab tab, FragmentTransaction ft) {
            Toast.makeText(SelectCusineByRestaurantId.this, "Reselected!", Toast.LENGTH_SHORT).show();
        }
    }
	
    @SuppressLint("ValidFragment")
   	private class TabContentFragment extends Fragment {
           private String mText;
    
           @SuppressLint("ValidFragment")
   		public TabContentFragment(String text) {
               mText = text;
           }
    
           public String getText() {
               return mText;
           }
    
           @Override
           public View onCreateView(LayoutInflater inflater, ViewGroup container,
                   Bundle savedInstanceState) {
               View fragView = inflater.inflate(R.layout.action_bar_tab_content, container, false);
    
               TextView text = (TextView) fragView.findViewById(R.id.text);
               text.setText(mText);
    
               return fragView;
           }
       }
    
    class  AsyncSubMenu extends AsyncTask{

		@Override
		protected Object doInBackground(Object... arg0) {
			// TODO Auto-generated method stub
			setSubMenuData();
			return null;
		}
    	
		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			  adapter=new CusineSubmenuAdapter( SelectCusineByRestaurantId.this, arr1);
			  lvExp.setAdapter( adapter );
		}
    }
    
    public void setSubMenuData()
	{
	
		HttpRequest hr = new HttpRequest();
		try {

			ContentValues cv = new ContentValues();
			// vp.onPageSelected(position);
			
			cv.put("RestaurantId", "4");
			cv.put("CusineId", "2");
			
			
			String res = hr.getDataFromServer(cv, "SelectFoodSubMenubyRestaurantandCusineId_ForApp");
			Log.d("Res" , res);
			isSuccess = true;
		//	return res;
			
			if(isSuccess)
			{
				JSONObject  jsonRootObject = new JSONObject(res);
				JSONArray jsonArray = jsonRootObject.optJSONArray("Table");
				 
				
				//JSONArray jsonArray = new JSONArray(res);
				Log.d("Json Object", jsonRootObject.toString());
				Log.d("Json Array", jsonArray.toString());
				
				 for(int i=0; i < jsonArray.length(); i++)
				 {
					 JSONObject jsonObject = jsonArray.getJSONObject(i);
					  
					 
			         Log.d("Json object item :", jsonObject.toString());
			         FoodSubMenuId = jsonObject.optString("FoodSubMenuId").toString();
		            Log.d(" FoodSubMenuId", FoodSubMenuId.toString());
		          
		             FoodSubMenuName = jsonObject.optString("FoodSubMenuName").toString();
		             Log.d("FoodSubMenuName",FoodSubMenuName.toString());
		           
		            FoodItemName= jsonObject.optString("FoodItemName").toString();
			            Log.d("FoodItemName",  FoodItemName.toString());
			         String[] var= FoodItemName.split(",");
			         
			         CusineSubmenuModel sched = new CusineSubmenuModel();
		             sched.setFoodSubMenuName( FoodSubMenuName);
		             arr1_child = new ArrayList<CusineSubMenuItemModel>();
		           
		             for(int j=0;j<var.length;j++)
		             {  CusineSubMenuItemModel child = new CusineSubMenuItemModel();
		            	 Log.d("FoodItem", var[j]);
		            	 child.setFoodItemName(var[j]);
		            	 arr1_child.add(child);
		             }
		            // child.setFoodItemName(FoodItemName);
			        // arr1_child.add(child);
		             sched.setItems(arr1_child);
					 arr1.add(sched);
			}
				
			    
				
			}
			else
			{
				Toast.makeText(this, "No associated Restaurants", Toast.LENGTH_SHORT).show();
			}
			 
			 
		} 
		catch (SocketTimeoutException e) {
			isSuccess = false;
			e.printStackTrace();
		} catch (SocketException e) {
			isSuccess = false;
			e.printStackTrace();
		} catch (IOException e) {
			isSuccess = false;
			e.printStackTrace();
		} 
		catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		 catch (JSONException e) {e.printStackTrace();}
	}
    
    /*****************  This function used by adapter ****************/
    public void onItemClick(int mPosition)
    {
       CusineSubmenuModel tempValues = ( CusineSubmenuModel ) arr1.get(mPosition);


       // SHOW ALERT                  

        Toast.makeText(SelectCusineByRestaurantId.this,""+tempValues.getFoodSubMenuName(),Toast.LENGTH_LONG).show();
    }
    
}
