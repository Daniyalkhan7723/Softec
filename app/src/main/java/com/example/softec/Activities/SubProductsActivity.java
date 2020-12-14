package com.example.softec.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.softec.MultipleProductSelection;
import com.example.softec.NewApis.OrderListListener;
import com.example.softec.NewApis.ModelClasses.SubProducts;
import com.example.softec.NewApis.ProductSelectionAdapter;
import com.example.softec.NewApis.SubProListener;
import com.example.softec.OrderCollectionClasses.OrderCollection;
import com.example.softec.OrderCollectionClasses.SubProductsIDList;
import com.example.softec.PojoClasses.CollectionList;
import com.example.softec.StaticClasses.StaticClass;
import com.example.softec.databinding.ActivitySubProductsBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubProductsActivity extends AppCompatActivity {

    ActivitySubProductsBinding a_sp_binding;
    StaticClass st;
    SubProducts subProducts, parentProduct;
    SubProListener subProListener;
    MultipleProductSelection multipleProductSelection;
    OrderCollection orderCollection;
    List<SubProductsIDList> subProductsIDLists = new ArrayList<>();

    int current_level = 2;
    int parent_level = 0;
    int parent_quantity = 0;

    Map level_map = new HashMap<String,SubProducts>();

    ArrayList<ProductSelectionAdapter> productSelectionAdapterArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        a_sp_binding = ActivitySubProductsBinding.inflate(getLayoutInflater());
        View view = a_sp_binding.getRoot();
        setContentView(view);
        initialize();
    }

    private void initialize() {

        st = new StaticClass(this);
        initOrderListener();

        multipleProductSelection = new MultipleProductSelection();

        if (getIntent() != null) {
            subProducts = (SubProducts) getIntent().getExtras().getSerializable("sub_products_data");
            parentProduct = subProducts;
            a_sp_binding.toppingsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            productSelectionAdapterArrayList.add(new ProductSelectionAdapter(this, subProducts, subProListener,1));

            a_sp_binding.toppingsRecyclerView.setAdapter(productSelectionAdapterArrayList.get(0));

            a_sp_binding.tvSubProductName.setText(subProducts.getName());

            parent_level = subProducts.getLevel();

            parent_quantity = subProducts.getQuantity();

            level_map.put(""+parent_level,subProducts);

            orderCollection = new
                    OrderCollection(
                            subProducts.getId(),
                            subProducts.getName(),
                            subProducts.getPrice(),
                            subProducts.getPrice(),
                     1,subProductsIDLists
            );
        }


        a_sp_binding.productSelectBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubProductsActivity.super.onBackPressed();
            }
        });


        a_sp_binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                orderCollection.setSubProductsIDLists(subProductsIDLists);
                returnData();

            }
        });

    }

    public static final String  orderCollection_item = "com.example.softec.OrderCollectionClasses";

    private void returnData(){

        Intent data = new Intent();
        data.putExtra(orderCollection_item,orderCollection);
        setResult(RESULT_OK,data);
        finish();
    }

    private void initOrderListener() {

        subProListener = new SubProListener() {

            @Override
            public void normalSelection(SubProducts subProducts, int index) {

                if(subProducts.getSub_products().get(index).getIs_multiple()==1){

                }
                else {productSelectionAdapterArrayList.add(new ProductSelectionAdapter(SubProductsActivity.this, subProducts.getSub_products().get(index), subProListener,0));
                a_sp_binding.toppingsRecyclerView.setAdapter(productSelectionAdapterArrayList.get(productSelectionAdapterArrayList.size()-1));
                a_sp_binding.toppingsRecyclerView.setAnimation(st.getOneInAll(8));}
            }

            @Override
            public void finalSelect(SubProducts subProducts, int index) {

                subProducts.setQuantity_check(subProducts.getQuantity_check()+1);

                if(subProducts.getQuantity()>subProducts.getQuantity_check()){
                    productSelectionAdapterArrayList.add(new ProductSelectionAdapter(SubProductsActivity.this,subProducts,subProListener,1));
                    a_sp_binding.toppingsRecyclerView.setAdapter(productSelectionAdapterArrayList.get(productSelectionAdapterArrayList.size()-1));
                }

                if(subProducts.getQuantity()==subProducts.getQuantity_check()){
                    for(int i=0;i<productSelectionAdapterArrayList.size();i++){
                        if(subProducts.getLevel()==productSelectionAdapterArrayList.get(i).getSubProducts(0).getLevel()){
                            a_sp_binding.toppingsRecyclerView.setAdapter(productSelectionAdapterArrayList.get(i));
                            break;
                        }
                    }
                }

                subProductsIDLists.add(new SubProductsIDList(subProducts.getSub_products().get(index)));
                a_sp_binding.toppingsRecyclerView.setAnimation(st.getOneInAll(8));
            }
        };

    }

    @Override
    public void onBackPressed() {

        if(productSelectionAdapterArrayList.size()>1) {

            if (a_sp_binding.toppingsRecyclerView.getAdapter() == productSelectionAdapterArrayList.get(productSelectionAdapterArrayList.size() - 1))

            {
                productSelectionAdapterArrayList.remove(productSelectionAdapterArrayList.size()-1);
            }

            a_sp_binding.toppingsRecyclerView.setAnimation(st.getOneInAll(6));
            a_sp_binding.toppingsRecyclerView.setAdapter(productSelectionAdapterArrayList.get(productSelectionAdapterArrayList.size()-1));

        }

        else {super.onBackPressed();}

    }

    private void showProduct(){}



    private void subtractProduct(){




    }

















}