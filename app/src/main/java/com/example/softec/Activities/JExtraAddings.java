package com.example.softec.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.softec.Activities.BaseActivity;
import com.example.softec.ExtraPanga.ExtraPangaAdapter;
import com.example.softec.MenuModel.ExtraAddingsCallBack;
import com.example.softec.MenuModel.MenuDealList;
import com.example.softec.MenuModel.MenuDealProduct;
import com.example.softec.MenuModel.MenuExtraAddings;
import com.example.softec.MenuModel.MenuProductSize;
import com.example.softec.R;
import com.example.softec.StaticClasses.Utils;
import com.example.softec.databinding.ActivityExtraAddingsBinding;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class JExtraAddings extends BaseActivity {

    ExtraAddingsCallBack extraAddingsCallBack;
    private ActivityExtraAddingsBinding binding;
    ArrayList<List<MenuExtraAddings>> j_list_menu_extra_adding = new ArrayList<>();
    int step = -1;
    MenuDealList menuDeal, menuDeal1;

    List<MenuExtraAddings> list_extra_addings_with_price = new ArrayList<>();
    List<MenuExtraAddings> list_extra_addings_with_no_price = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExtraAddingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initialize();

        findViewById(R.id.iv_back_extra_addings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initialize() {

        LinkedList<Integer> category_id_list = new LinkedList<>();
        LinkedList<Integer> category_counter_list = new LinkedList<>();

        binding.nextBtn.setVisibility(View.GONE);

        initextraAddingsCallBack();

        binding.extraAddingRecycler.setLayoutManager(new LinearLayoutManager(this));
        if (getIntent() != null) {
            menuDeal = (MenuDealList) getIntent().getSerializableExtra("extra_adding");
            menuDeal1 = (MenuDealList) getIntent().getSerializableExtra("extra_adding");;
        }

        binding.tvDealName.setText(menuDeal.getName());

        for (int i = 0; i < menuDeal.getExtraAddings().size(); i++) {

            double price = Double.parseDouble(menuDeal.getExtraAddings().get(i).getPrice());

            if (price > 0) {
                list_extra_addings_with_price.add(menuDeal.getExtraAddings().get(i));
            } else {

                list_extra_addings_with_no_price.add(menuDeal.getExtraAddings().get(i));


                if (category_id_list.size() == 0) {
                    category_id_list.add(menuDeal.getExtraAddings().get(i).getCategory_id());
                    category_counter_list.add(menuDeal.getExtraAddings().get(i).getCounter());
                } else if (category_id_list.size() > 0 && !category_id_list.contains(menuDeal.getExtraAddings().get(i).getCategory_id())) {
                    category_id_list.add(menuDeal.getExtraAddings().get(i).getCategory_id());
                    category_counter_list.add(menuDeal.getExtraAddings().get(i).getCounter());
                }

            }
        }

        for (int i = 0; i < category_id_list.size(); i++) {

            int id = category_id_list.get(i);
            int cat_Counter = category_counter_list.get(i) == 0 ? 1 : category_counter_list.get(i);

            for (int j = 0; j < cat_Counter; j++) {

                List<MenuExtraAddings> categorized_extra_adding_lists = new ArrayList<>();

                for (int k = 0; k < list_extra_addings_with_no_price.size(); k++) {

                    if (id == list_extra_addings_with_no_price.get(k).getCategory_id()) {
                        categorized_extra_adding_lists.add(list_extra_addings_with_no_price.get(k));
                    }
                }

                j_list_menu_extra_adding.add(categorized_extra_adding_lists);

            }

        }

        if (list_extra_addings_with_price.size() > 0) {
            j_list_menu_extra_adding.add(list_extra_addings_with_price);
        }

        nextLay();

        checkAll();

    }

    private void initextraAddingsCallBack() {

        extraAddingsCallBack = new ExtraAddingsCallBack() {

            @Override
            public void replaceExtraAddings(MenuExtraAddings menuExtraAddings, boolean add, boolean remove) {
                replace_with_extra_adding(menuExtraAddings, add, remove);
            }

            @Override
            public void multipleExtraAddings(MenuExtraAddings extraAddings, boolean add, boolean remove) {
                addMultipleExtra(extraAddings, add, remove);
            }

            @Override
            public void moveToNextLay() {
            }

            @Override
            public void moveToPreviousLay() {
                previousLay();
            }

            @Override
            public void addExtraAdding(MenuExtraAddings menuExtraAddings, boolean add) {
                addNewExtraAdding(menuExtraAddings);
            }

            @Override
            public void addProductSize(MenuProductSize menuProductSize) {
            }
        };

        addToCart();

    }

    private int last_count = 0;

    private void addMultipleExtra(MenuExtraAddings extraAddings, boolean add, boolean remove) {

        if (add) {
            list_of_manage_extra_addings.add(extraAddings);
            menuDeal1.setPrice(menuDeal1.getPrice()+(extraAddings.getPrice().length()==0?0.0:Double.parseDouble(extraAddings.getPrice())));
            last_count++;
        } else if (remove) {
            for (int i = 0; i < list_of_manage_extra_addings.size(); i++) {
                if (extraAddings.getId() == list_of_manage_extra_addings.get(i).getId()) {
                    list_of_manage_extra_addings.remove(i);
                    menuDeal1.setPrice(menuDeal1.getPrice()-(extraAddings.getPrice().length()==0?0.0:Double.parseDouble(extraAddings.getPrice())));
                    break;
                }
            }
            last_count--;
        }
        checkAll();
    }

    List<MenuDealProduct> deletedDealProducts = new ArrayList<>();

    private void replace_with_extra_adding(MenuExtraAddings menuExtraAddings, boolean replace, boolean remove) {

        int replace_of = menuExtraAddings.getReplaceOf().getReplace_of();

        if (replace) {

            last_count++;

            for (int i = 0; i < menuDeal1.getDealProducts().size(); i++) {

                if (replace_of == menuDeal1.getDealProducts().get(i).getId()) {
                    deletedDealProducts.add(menuDeal1.getDealProducts().get(i));
                    menuDeal1.getDealProducts().remove(i);
                    menuDeal1.setPrice(menuDeal1.getPrice()+(menuExtraAddings.getPrice().length()==0?0.0:Double.parseDouble(menuExtraAddings.getPrice())));
                    list_of_manage_extra_addings.add(menuExtraAddings);
                    break;
                }

            }

        } else if (remove) {

            last_count--;

            for (int i = 0; i < deletedDealProducts.size(); i++) {
                if (replace_of == deletedDealProducts.get(i).getId()) {
                    menuDeal1.getDealProducts().add(deletedDealProducts.get(i));
                    deletedDealProducts.remove(i);
                    menuDeal1.setPrice(menuDeal1.getPrice()-(menuExtraAddings.getPrice().length()==0?0.0:Double.parseDouble(menuExtraAddings.getPrice())));
                    list_of_manage_extra_addings.remove(menuExtraAddings);
                    break;
                }
            }


        }

        checkAll();

    }


    private void addToCart(){
        // ok work
        binding.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hoverEffect(v);
                menuDeal1.setExtraAddings(list_of_manage_extra_addings);
                int id_Sum = 0;
                // update idSum
                for (int i = 0; i < menuDeal1.getDealProducts().size(); i++) {
                    id_Sum = id_Sum + menuDeal1.getDealProducts().get(i).getId();
                }
                for (int i = 0; i < menuDeal1.getExtraAddings().size(); i++) {
                    id_Sum = id_Sum + menuDeal1.getExtraAddings().get(i).getId();
                }

                Intent data = new Intent();
                data.putExtra(Utils.menu_order_item, menuDeal1);
                data.putExtra("idSum", id_Sum);
                data.putExtra("type", Utils.typeDeal);
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }

    private void nextLay() {
        if (step < j_list_menu_extra_adding.size() - 1) {
            step++;
            binding.extraAddingRecycler.setAnimation(st.getSelectedAnim(4));
            setAdapter();
        } else {
            step++;
            binding.extraAddingRecycler.setVisibility(View.GONE);
            binding.nextBtn.setVisibility(View.VISIBLE);
            binding.tvStepName.setVisibility(View.GONE);
        }
    }

    private void previousLay() {
        binding.nextBtn.setVisibility(View.GONE);
        if (step > 0) {
            step--;
            removeExtraAddingOnBack(step + 1);
            binding.extraAddingRecycler.setAnimation(st.getSelectedAnim(3));
            setAdapter();
        }
    }

    List<MenuExtraAddings> list_of_manage_extra_addings = new ArrayList<>();

    private void addNewExtraAdding(MenuExtraAddings extraAddings) {

        list_of_manage_extra_addings.add(extraAddings);
        checkAll();
        nextLay();
    }


    private void removeExtraAddingOnBack(int stepp) {
        if (last_count > 0) {

            for (int i = 0; i < deletedDealProducts.size(); i++) {
                menuDeal1.getDealProducts().add(deletedDealProducts.get(i));
            }

            deletedDealProducts.clear();

            for (int j = 0; j < last_count; j++) {
                double myPrice = (list_of_manage_extra_addings.get(list_of_manage_extra_addings.size() - 1).getPrice().length()==0?0.0:Double.parseDouble(list_of_manage_extra_addings.get(list_of_manage_extra_addings.size() - 1).getPrice()));
                menuDeal1.setPrice(menuDeal1.getPrice()-myPrice);
                list_of_manage_extra_addings.remove(list_of_manage_extra_addings.size() - 1);
            }

            for (int j = 0; j < j_list_menu_extra_adding.get(stepp).size(); j++) {
                j_list_menu_extra_adding.get(stepp).get(j).setSelected(false);
            }

            last_count = 0;
        }

        if (list_of_manage_extra_addings.size() > 0) {
            list_of_manage_extra_addings.remove(list_of_manage_extra_addings.size() - 1);
        }

        checkAll();
    }


    private void checkAll() {

        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < menuDeal1.getDealProducts().size(); i++) {

            buffer.append(menuDeal1.getDealProducts().get(i).getName()+"\n");

        }

        for (int i = 0; i < list_of_manage_extra_addings.size(); i++) {
            buffer.append(list_of_manage_extra_addings.get(i).getName()+"\n");
        }

        binding.tvDealProduct.setText(buffer.toString());
        binding.tvPrice.setText("$"+menuDeal1.getPrice());

    }

    private void setAdapter() {

        binding.extraAddingRecycler.setVisibility(View.VISIBLE);
        binding.extraAddingRecycler.setAdapter(new ExtraPangaAdapter(j_list_menu_extra_adding.get(step), extraAddingsCallBack, this,
                (list_extra_addings_with_price.size() > 0 && step == j_list_menu_extra_adding.size() - 1) ? 1 : 0));
        binding.nextBtn.setVisibility((list_extra_addings_with_price.size() > 0 && step == j_list_menu_extra_adding.size() - 1) ? View.VISIBLE: View.GONE);

        String cat_name = j_list_menu_extra_adding.get(step).get(0).getCategory_name();

        cat_name = cat_name.length()==0?"Others":(list_extra_addings_with_price.size() > 0 && step == j_list_menu_extra_adding.size() - 1)?"Extra Addings":cat_name;

        binding.tvStepName.setVisibility(View.VISIBLE);
        binding.tvStepName.setText(cat_name);

    }


    @Override
    public void onBackPressed() {
        if (step > 0) {
            previousLay();
        } else if (step == 0) {
            super.onBackPressed();
        }
    }
}