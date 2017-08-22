package com.example.user.mobileproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by user on 2016-05-29.
 */
public class Fragment_home extends Fragment {
    GridAdapter gridAdapter;
    listviewAdapter listviewAdapter;

    View view;

    LinearLayout layout_grid;
    LinearLayout layout_list;
    LinearLayout layout_name;

    shelterClass[] shelter = null; //전체 값
    shelterClass[] shelter_temp = null; //해당 하는 지역 값
    int flag = 0; //0일때 첫 화면, 1일때 서울, 2일때 경기도, 3일때 부산, 4일때 대구, 5일때 인천, 6일때 광주, 7일때 대전, 8일때 울산, 9일때 강원도, 10일때 충청북도, 11일때 충청남도, 12일때 전라북도, 13일때 전라남도, 14일때 경상북도
    //15일때 경상남도, 16일때 제주도 모든 선택화면 마다 버튼 이미지

    GridView gridView;
    ListView listView;

    ArrayList<Integer> ImageList = new ArrayList<Integer>(); //처음 이미지 리스트
    ArrayList<String> regionList = new ArrayList<String>(); //처음 지역 리스트

    ArrayList<shelterClass> shelterList = new ArrayList<shelterClass>(); //선택했을때 해당되는 놈들

    ArrayList<String> regionList2 = new ArrayList<String>(); //새로운 프레그먼트에 돌려막기 할 용
    ArrayList<String> regionList3 = new ArrayList<String>();


    public Fragment_home(){
    };

    public void setshlterClass(ArrayList<shelterClass> shelterClasses)
    {
        shelter = (shelterClass[]) shelterClasses.toArray(new shelterClass[shelterClasses.size()]);
        String str;
    }

    public void setImageList()
    {
        ////////////////////////////이미지 바꾸기/////////////////////////
        ImageList.add(R.drawable.mseoul);
        ImageList.add(R.drawable.mgyeonggi);
        ImageList.add(R.drawable.mbusan);
        ImageList.add(R.drawable.mdaegu);
        ImageList.add(R.drawable.mincheon);
        ImageList.add(R.drawable.mgwangju);
        ImageList.add(R.drawable.mdaejeon);
        ImageList.add(R.drawable.mulsan);
        ImageList.add(R.drawable.mgangwon);
        ImageList.add(R.drawable.mcungbuk);
        ImageList.add(R.drawable.mcungnam);
        ImageList.add(R.drawable.mjeonbuk);
        ImageList.add(R.drawable.mjeonnam);
        ImageList.add(R.drawable.mgyungbuk);
        ImageList.add(R.drawable.mgyungnam);
        ImageList.add(R.drawable.mjeju);
        ////////////////////////////////////////////////////////////////

        regionList.add("서울");
        regionList.add("경기");
        regionList.add("부산");
        regionList.add("대구");
        regionList.add("인천");
        regionList.add("광주");
        regionList.add("대전");
        regionList.add("울산");
        regionList.add("강원");
        regionList.add("충북");
        regionList.add("충남");
        regionList.add("전북");
        regionList.add("전남");
        regionList.add("경북");
        regionList.add("경남");
        regionList.add("제주");


    }

    public void setRegionList(){

        shelter_temp = (shelterClass[])shelterList.toArray(new shelterClass[shelterList.size()]);
        Log.d("test",String.valueOf(shelterList.size()));

        for(int i=0; i<shelterList.size();i++) {
            shelterClass sc = new shelterClass(shelter_temp[i].region, shelter_temp[i].region2, shelter_temp[i].name, shelter_temp[i].type, shelter_temp[i].gender, shelter_temp[i].address);
            regionList2.add(sc.name);
        }
        for(int i=0; i<shelterList.size();i++) {
            shelterClass sc = new shelterClass(shelter_temp[i].region, shelter_temp[i].region2, shelter_temp[i].name, shelter_temp[i].type, shelter_temp[i].gender, shelter_temp[i].address);
            regionList3.add(sc.address);
        }
        listviewAdapter.notifyDataSetChanged();

        shelterList.clear();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container , false);

        layout_list = (LinearLayout)view.findViewById(R.id.fragment_home_layout_listview);
        layout_grid = (LinearLayout)view.findViewById(R.id.fragment_home_layout_gridview);

        /////////////////////////첫번째 레이아웃///////////////////////////////////////

        gridView = (GridView)view.findViewById(R.id.home_grideview);
        setImageList();
        gridAdapter = new GridAdapter(getActivity());
        gridView.setAdapter(gridAdapter);

        //////////////////////////////////////////////////////////////////////////////

        layout_list.setVisibility(View.GONE);
        listviewAdapter = new listviewAdapter(getActivity());
        listView = (ListView)view.findViewById(R.id.fragment_home_listview);
        listView.setAdapter(listviewAdapter);

        ///////////////////////////Back Button/////////////////////////////
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if(flag == 1)
                    {
                        regionList2.clear();
                        listviewAdapter.notifyDataSetChanged();

                        layout_grid.setVisibility(View.VISIBLE);
                        layout_list.setVisibility(View.GONE);

                        flag = 0;

                        return true;
                    }
                    else {
                        return false;
                    }
                }
                else {
                    return false;
                }
            }
        });
        //////////////////////////////////////////////////////////////////


        AnroidToServer server = new AnroidToServer();
        server.serachAll("all", new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, Response response) {
                Log.d("test", jsonObject.toString());

                ArrayList<shelterClass> shelterClasses = new ArrayList<shelterClass>();

                JsonArray result = jsonObject.getAsJsonArray("result");
                for(int i=0;i<result.size();i++)
                {
                    JsonObject n1 = (JsonObject)result.get(i);
                    String region = n1.get("region").getAsString();
                    String region2 = n1.get("region2").getAsString();
                    String type = n1.get("type").getAsString();
                    String gender = n1.get("gender").getAsString();
                    String name = n1.get("name").getAsString();
                    String address = n1.get("address").getAsString();
                    shelterClass s1 = new shelterClass(region,region2,name,type,gender,address);
                    shelterClasses.add(s1);
                }
                setshlterClass(shelterClasses);

                /////////////////////////////////실험용////////////////////////////////
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("test", error.toString());
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getActivity(),RegionIntent.class);
                    intent.putExtra("sc",shelter_temp[position]);
                    startActivity(intent);
            }
        });

        return view;
    }


    public class GridAdapter extends BaseAdapter{
        private Context context;

        public GridAdapter(Context context)
        {
            this.context = context;
        }

        @Override
        public int getCount() {
            return ImageList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) { //움직일때 마다 불림
            final ViewHolder viewHolder;
            if(convertView ==null)
            {
                ////////////////////////////// 초기화/////////////////////////////////
                viewHolder = new ViewHolder();
                convertView = (getActivity()).getLayoutInflater().inflate(R.layout.gride_item,null);

                viewHolder.textView = (TextView)convertView.findViewById(R.id.gride_view_text);
                viewHolder.imageView = (ImageView)convertView.findViewById(R.id.gride_view_img);

                convertView.setTag(viewHolder); //view에다가 값을 저장해놓을 수 있다.
                //////////////////////////////////////////////////////////////////////////
            }
            else
            {
                viewHolder = (ViewHolder)convertView.getTag();
            }
            viewHolder.textView.setText(regionList.get(position));
            viewHolder.imageView.setImageResource(ImageList.get(position));

            viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (flag == 0) { // 0일때 이제 누르면 리스트뷰를 보여줘야 한다.
                        for (int i = 0; i < shelter.length; i++) {
                            String str = shelter[i].region;
                            if (str.equals(regionList.get(position))) {
                                shelterClass sc = new shelterClass(shelter[i].region, shelter[i].region2, shelter[i].name, shelter[i].type, shelter[i].gender, shelter[i].address);
                                shelterList.add(sc);
                                continue;
                            }
                        }
                        ImageView nimageView = (ImageView)getActivity().findViewById(R.id.home_image_name);
                        TextView ntextView = (TextView)getActivity().findViewById(R.id.home_tv_name);
                        switch (position) {
                            case 0:

                                setRegionList();
                                flag = 1;

                                layout_grid.setVisibility(View.GONE);
                                layout_list.setVisibility(View.VISIBLE);


                                nimageView.setImageResource(R.drawable.mseoul);
                                ntextView.setText(regionList.get(position));

                                break;
                            case 1:
                                setRegionList();
                                flag =1;

                                layout_grid.setVisibility(View.GONE);
                                layout_list.setVisibility(View.VISIBLE);
                                nimageView.setImageResource(R.drawable.mgyeonggi);
                                ntextView.setText(regionList.get(position));
                                break;
                            case 2:

                                setRegionList();
                                flag =1;

                                layout_grid.setVisibility(View.GONE);
                                layout_list.setVisibility(View.VISIBLE);
                                nimageView.setImageResource(R.drawable.mbusan);
                                ntextView.setText(regionList.get(position));
                                break;
                            case 3:

                                setRegionList();
                                flag =1;

                                layout_grid.setVisibility(View.GONE);
                                layout_list.setVisibility(View.VISIBLE);
                                nimageView.setImageResource(R.drawable.mdaegu);
                                ntextView.setText(regionList.get(position));
                                break;
                            case 4:

                                setRegionList();
                                flag =1;

                                layout_grid.setVisibility(View.GONE);
                                layout_list.setVisibility(View.VISIBLE);
                                nimageView.setImageResource(R.drawable.mincheon);
                                ntextView.setText(regionList.get(position));
                                break;
                            case 5:

                                setRegionList();
                                flag =1;

                                layout_grid.setVisibility(View.GONE);
                                layout_list.setVisibility(View.VISIBLE);
                                nimageView.setImageResource(R.drawable.mgwangju);
                                ntextView.setText(regionList.get(position));
                                break;
                            case 6:

                                setRegionList();
                                flag =1;

                                layout_grid.setVisibility(View.GONE);
                                layout_list.setVisibility(View.VISIBLE);
                                nimageView.setImageResource(R.drawable.mdaejeon);
                                ntextView.setText(regionList.get(position));
                                break;
                            case 7:

                                setRegionList();
                                flag =1;

                                layout_grid.setVisibility(View.GONE);
                                layout_list.setVisibility(View.VISIBLE);
                                nimageView.setImageResource(R.drawable.mulsan);
                                ntextView.setText(regionList.get(position));
                                break;
                            case 8:

                                setRegionList();
                                flag =1;

                                layout_grid.setVisibility(View.GONE);
                                layout_list.setVisibility(View.VISIBLE);
                                nimageView.setImageResource(R.drawable.mgangwon);
                                ntextView.setText(regionList.get(position));
                                break;
                            case 9:

                                setRegionList();
                                flag =1;

                                layout_grid.setVisibility(View.GONE);
                                layout_list.setVisibility(View.VISIBLE);
                                nimageView.setImageResource(R.drawable.mcungbuk);
                                ntextView.setText(regionList.get(position));
                                break;
                            case 10:

                                setRegionList();
                                flag =1;

                                layout_grid.setVisibility(View.GONE);
                                layout_list.setVisibility(View.VISIBLE);
                                nimageView.setImageResource(R.drawable.mcungnam);
                                ntextView.setText(regionList.get(position));
                                break;
                            case 11:

                                setRegionList();
                                flag =1;

                                layout_grid.setVisibility(View.GONE);
                                layout_list.setVisibility(View.VISIBLE);
                                nimageView.setImageResource(R.drawable.mjeonbuk);
                                ntextView.setText(regionList.get(position));
                                break;
                            case 12:

                                setRegionList();
                                flag =1;

                                layout_grid.setVisibility(View.GONE);
                                layout_list.setVisibility(View.VISIBLE);
                                nimageView.setImageResource(R.drawable.mjeonnam);
                                ntextView.setText(regionList.get(position));
                                break;
                            case 13:

                                setRegionList();
                                flag =1;


                                layout_grid.setVisibility(View.GONE);
                                layout_list.setVisibility(View.VISIBLE);
                                nimageView.setImageResource(R.drawable.mgyungbuk);
                                ntextView.setText(regionList.get(position));
                                break;
                            case 14:

                                setRegionList();
                                flag =1;

                                layout_grid.setVisibility(View.GONE);
                                layout_list.setVisibility(View.VISIBLE);
                                nimageView.setImageResource(R.drawable.mgyungnam);
                                ntextView.setText(regionList.get(position));
                                break;
                            case 15:

                                setRegionList();
                                flag =1;

                                layout_grid.setVisibility(View.GONE);
                                layout_list.setVisibility(View.VISIBLE);
                                nimageView.setImageResource(R.drawable.mjeju);
                                ntextView.setText(regionList.get(position));
                                break;
                        }
                    }
                }
            });

            return convertView;
        }

        public class ViewHolder{
            public ImageView imageView;
            public TextView textView;
        }
    }

    public class listviewAdapter extends BaseAdapter{
        Context context;
        public listviewAdapter(Context context)
        {
            this.context = context;
        }
        @Override
        public int getCount() {
            return regionList2.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            if(convertView ==null)
            {
                ////////////////////////////// 초기화/////////////////////////////////
                viewHolder = new ViewHolder();
                convertView = (getActivity()).getLayoutInflater().inflate(R.layout.listview_item,null);

                viewHolder.textView = (TextView)convertView.findViewById(R.id.list_item);
                viewHolder.textView2 = (TextView)convertView.findViewById(R.id.list_item2);
                convertView.setTag(viewHolder); //view에다가 값을 저장해놓을 수 있다.
                //////////////////////////////////////////////////////////////////////////
            }
            else
            {
                viewHolder = (ViewHolder)convertView.getTag();
            }
            viewHolder.textView.setText(regionList2.get(position));
            viewHolder.textView2.setText(regionList3.get(position));



            return convertView;
        }
        public class ViewHolder{

            public TextView textView;
            public TextView textView2;

        }
    }
}
