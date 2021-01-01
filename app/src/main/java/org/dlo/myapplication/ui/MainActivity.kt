package org.dlo.myapplication.ui.util

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_main.*
import org.dlo.myapplication.R
import org.dlo.myapplication.data.GithubUser
import org.dlo.myapplication.ui.home.HomeAdapter
import org.dlo.myapplication.ui.home.HomeViewModel


class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName
    private val viewModel: HomeViewModel by lazy { ViewModelProviders.of(this).get(HomeViewModel::class.java) }
    val adapter : HomeAdapter by lazy { HomeAdapter(mutableListOf()) }
    private var searchDisposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar by lazy { toolbar_main_activity }
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        with(viewModel) {
            usersData.observe(this@MainActivity, Observer {
                initView(it)
            })

            error.observe(this@MainActivity, Observer {
                progressBar_home.visibility = View.GONE
                Toast.makeText(this@MainActivity, "$it", Toast.LENGTH_LONG).show()
            })
            initSearchBar()
        }
    }

    private fun initView(it: GithubUser?) {
        rv_main_home.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        rv_main_home.adapter = adapter
        progressBar_home.visibility = View.GONE

        if (it != null && it.items.isNotEmpty()) {
            adapter.clear()
            adapter.add(it.items)

        } else {
            Toast.makeText(this@MainActivity, getString(R.string.empty_list), android.widget.Toast.LENGTH_LONG).show()
        }
    }

    fun initSearchBar() {
        search_view.setOnEditorActionListener({ v, actionId, event ->
            if (actionId === EditorInfo.IME_ACTION_SEARCH) {
                searchQuery(v.getText().toString())
                hideSoftkeyboard()
                true
            } else
            false
        })
    }

    private fun hideSoftkeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(search_view.getWindowToken(), 0)
    }

    private fun searchQuery(query: String) {

        clearSearchList()
        if (query.length > 0) {
            showLoader()
            viewModel.fetchUsers(query)
        }
    }

    private fun showLoader() {
        progressBar_home.visibility = View.VISIBLE
    }

    private fun clearSearchList() {
        adapter.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        searchDisposable?.dispose()
    }
}
