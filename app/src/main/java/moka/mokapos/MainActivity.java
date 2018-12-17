package moka.mokapos;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import moka.mokapos.model.Discount;
import moka.mokapos.model.ShoppingItem;
import moka.mokapos.ui.BaseFragment;
import moka.mokapos.ui.CartFragment;
import moka.mokapos.ui.CartItemEditorPopUp;
import moka.mokapos.ui.DiscountFragment;
import moka.mokapos.ui.LibraryFragment;
import moka.mokapos.ui.ShoppingItemListAdapter;
import moka.mokapos.ui.ShoppingListFragment;


public class MainActivity extends AppCompatActivity implements LibraryFragment.OnLeftPanelOptionClickListener, BaseFragment.OnHeaderBackClickListener,
        ShoppingItemListAdapter.OnItemSelectedListener {

    final int FRAGMENT_LIBRARY = 0;
    final int FRAGMENT_DISCOUNT = 1;
    final int FRAGMENT_SHOPPING_ITEMS_LIST = 2;

    int currentFragment = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //download data if not already
        if (!SharedPreferenceHelper.getInstance(this).itemsFetched()) {
            if (NetworkUtil.isNetworkAvailable(this)) {
                downloadData();
            } else {
                View loadingView = findViewById(R.id.loading_view);
                findViewById(R.id.loading_view).setVisibility(View.VISIBLE);
                findViewById(R.id.progress_bar).setVisibility(View.GONE);
                findViewById(R.id.error_msg).setVisibility(View.VISIBLE);
                loadingView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (NetworkUtil.isNetworkAvailable(MainActivity.this)) {
                            findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
                            findViewById(R.id.error_msg).setVisibility(View.GONE);
                            downloadData();
                        }
                    }
                });
            }
        } else {
            CartManager.getInstance(this);
            findViewById(R.id.loading_view).setVisibility(View.GONE);
            attachFragments();
        }


    }

    private void attachFragments() {
        FragmentManager manager = getSupportFragmentManager();
        LibraryFragment library = new LibraryFragment();
        library.setOnOptionClickListener(this);
        CartFragment cartFragment = new CartFragment();
        manager.popBackStack(LibraryFragment.class.getSimpleName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        manager.beginTransaction().add(R.id.left_container, library, LibraryFragment.class.getSimpleName()).commit();
        manager.beginTransaction().add(R.id.right_container, cartFragment).commit();

    }

    private void changeLeftFragment(int which) {
        if (currentFragment == which)
            return;
        FragmentManager manager = getSupportFragmentManager();
        Fragment f;

        switch (which) {
            case FRAGMENT_DISCOUNT:
                f = manager.findFragmentByTag(DiscountFragment.class.getSimpleName());
                if (f == null) {
                    f = new DiscountFragment();
                }
                break;
            case FRAGMENT_LIBRARY:
                f = manager.findFragmentByTag(LibraryFragment.class.getSimpleName());
                if (f == null) {
                    f = new LibraryFragment();
                    ((LibraryFragment) f).setOnOptionClickListener(this);
                }
                break;
            default:
                f = manager.findFragmentByTag(ShoppingListFragment.class.getSimpleName());
                if (f == null) {
                    f = new ShoppingListFragment();
                    ((ShoppingListFragment) f).setOnItemSelectedListener(this);
                }

        }

        ((BaseFragment) f).setOnHeaderBackClickListener(this);
        manager.beginTransaction().replace(R.id.left_container, f, f.getClass().getSimpleName()).commit();
        currentFragment = which;
    }

    private void downloadData() {
        new DownloadHelper(this, new DownloadHelper.OnCompleteListener() {
            @Override
            public void onSuccess() {
                View loadingView = findViewById(R.id.loading_view);
                loadingView.setOnClickListener(null);
                loadingView.setVisibility(View.GONE);
                attachFragments();

            }
        }).downloadAndSaveToDB();
    }

    @Override
    public void onOptionClicked(int id) {
        switch (id) {
            case R.id.all_items:
                changeLeftFragment(FRAGMENT_SHOPPING_ITEMS_LIST);
                break;
            default:
                changeLeftFragment(FRAGMENT_DISCOUNT);
                break;
        }
    }

    @Override
    public void onHeaderBackClicked() {
        if (currentFragment != FRAGMENT_LIBRARY) {
            changeLeftFragment(FRAGMENT_LIBRARY);
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager manager = getSupportFragmentManager();
        Fragment f = manager.findFragmentById(R.id.left_container);

        if (f != null && !(f instanceof LibraryFragment)) {
            changeLeftFragment(FRAGMENT_LIBRARY);
        } else
            super.onBackPressed();
    }

    //callback when any item from shopping list is selected
    @Override
    public void onItemSelected(ShoppingItem item) {
        CartItemEditorPopUp popUp = new CartItemEditorPopUp(this, item.getId(), 1, Discount.getRandomDiscount());
        popUp.showAtLocation(findViewById(R.id.root_view), Gravity.CENTER, 0, 0);
    }

}
