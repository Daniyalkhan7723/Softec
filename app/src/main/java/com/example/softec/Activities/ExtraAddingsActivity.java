package com.example.softec.Activities;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.softec.CategorySplition;
import com.example.softec.ExtraPanga.ExtraPangaAdapter;
import com.example.softec.MenuModel.MenuExtraAddingDeal1;
import com.example.softec.StaticClasses.SharedPreference;
import com.example.softec.adapter.ExtraAddingAdapter;
import com.example.softec.adapter.ExtraAddingAdapterProductSize;
import com.example.softec.MenuModel.ExtraAddingsCallBack;
import com.example.softec.MenuModel.MenuDealList;
import com.example.softec.MenuModel.MenuDealProduct;
import com.example.softec.MenuModel.MenuExtraAddings;
import com.example.softec.MenuModel.MenuProductList;
import com.example.softec.MenuModel.MenuProductSize;
import com.example.softec.R;
import com.example.softec.StaticClasses.Utils;
import com.example.softec.adapter.ExtraAddingsDealLayout1;
import com.example.softec.databinding.ActivityExtraAddingsBinding;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ExtraAddingsActivity extends BaseActivity {

    private ActivityExtraAddingsBinding binding;
    ExtraAddingsCallBack extraAddingsCallBack;
    ExtraAddingAdapterProductSize extraAddingAdapterProductSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExtraAddingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.tvStepName.setVisibility(View.GONE);
        binding.extraAddingRecycler.setLayoutManager(new LinearLayoutManager(this));

        if (getIntent() != null) {
            initextraAddingsCallBack();
            productFunction();
        }

        binding.ivBackExtraAddings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initextraAddingsCallBack() {

        extraAddingsCallBack = new ExtraAddingsCallBack() {

            @Override
            public void replaceExtraAddings(MenuExtraAddings menuExtraAddings, boolean add, boolean remove) {

            }

            @Override
            public void multipleExtraAddings(MenuExtraAddings extraAddings,boolean add,boolean remove) {

            }

            @Override
            public void moveToNextLay() {}

            @Override
            public void moveToPreviousLay() {}
            @Override
            public void addExtraAdding(MenuExtraAddings menuExtraAddings, boolean add) {
            }

            @Override
            public void addProductSize(MenuProductSize menuProductSize) {
                binding.nextBtn.setVisibility(View.VISIBLE);
                List<MenuProductSize> menuProductSizes = new ArrayList<>();
                menuProductSizes.add(menuProductSize);
                menuProductList.setMenuProductSizeList(menuProductSizes);
                setProductPrice(menuProductSize.getPrice());
            }
        };

    }

    private void setProductPrice(double price) {
        binding.tvPrice.setVisibility(View.VISIBLE);
        menuProductList.setPrice(price);
        binding.tvPrice.setText("$" + menuProductList.getPrice());
    }

    private MenuProductList menuProductList;

    private void productFunction() {

        menuProductList = (MenuProductList) getIntent().getSerializableExtra("extra_adding");
        binding.tvDealName.setText(menuProductList.getName());
        binding.tvDealProduct.setText("Select " + menuProductList.getName() + " Size");
        binding.nextBtn.setVisibility(View.GONE);
        binding.tvPrice.setVisibility(View.GONE);

        extraAddingAdapterProductSize = new ExtraAddingAdapterProductSize(this, menuProductList.getMenuProductSizeList(), extraAddingsCallBack);
        binding.extraAddingRecycler.setAdapter(extraAddingAdapterProductSize);

        binding.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hoverEffect(v);
                int id_Sum = menuProductList.getId() + menuProductList.getMenuProductSizeList().get(0).getId();
                Intent data = new Intent();
                data.putExtra(Utils.menu_order_item, menuProductList);
                data.putExtra("idSum", id_Sum);
                data.putExtra("type", Utils.typeProduct);
                setResult(RESULT_OK, data);
                finish();

            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}