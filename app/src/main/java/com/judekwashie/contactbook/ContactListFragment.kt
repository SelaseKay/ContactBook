package com.judekwashie.contactbook

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.judekwashie.contactbook.databinding.FragmentContactListBinding
import kotlin.math.hypot


class ContactListFragment : Fragment(), MenuItem.OnActionExpandListener {

    private var _binding: FragmentContactListBinding? = null
    private val binding get() = _binding!!

    private val toolbar by lazy {
        binding.appbar.toolbar
    }

    private lateinit var searchItem: MenuItem
    private lateinit var searchView: SearchView
    private lateinit var addContactMenuItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search_add, menu)

        searchItem = menu.findItem(R.id.search_action).apply {
            setOnActionExpandListener(this@ContactListFragment)
        }

        addContactMenuItem = menu.findItem(R.id.add_contact_item)

        searchView = (searchItem.actionView as SearchView).apply {
            setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.search_view_color))
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentContactListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.search_action -> {
                startCircularRevealAnimation(toolbar, false)
                true
            }
            R.id.add_contact_item -> {
                true
            }
            else -> {
                false
            }
        }
    }

    private fun startCircularRevealAnimation(view: View, isCollapsing: Boolean) {
        var cx = view.width
        var cy = view.height
        var finalRadius = hypot(cx.toDouble(), cy.toDouble()).toFloat()

        val anim = if (isCollapsing) {
            cx = 0
            cy = view.height
            finalRadius = (view.width).toFloat()
            ViewAnimationUtils.createCircularReveal(view, cx, cy, 0f, finalRadius)
        } else {
            ViewAnimationUtils.createCircularReveal(view, cx, cy, 0f, finalRadius)
        }

        anim.apply {
            duration = 300
            start()
        }
    }

    override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
        toolbar.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.search_view_color
            )
        )
        addContactMenuItem.isVisible = false
        return true
    }

    override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
        startCircularRevealAnimation(toolbar, true)
        toolbar.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
        addContactMenuItem.isVisible = true
        return true
    }


}