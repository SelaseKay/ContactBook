package com.judekwashie.contactbook.ui.contactlist

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.judekwashie.contactbook.R
import com.judekwashie.contactbook.RecyclerItemClickListener
import com.judekwashie.contactbook.adapter.ContactRecyclerAdapter
import com.judekwashie.contactbook.data.entities.Contact
import com.judekwashie.contactbook.databinding.FragmentContactListBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.math.hypot

@AndroidEntryPoint
class ContactListFragment : Fragment(), MenuItem.OnActionExpandListener, RecyclerItemClickListener {

    private var _binding: FragmentContactListBinding? = null
    private val binding get() = _binding!!

    private lateinit var toolbar: Toolbar
    private lateinit var addContactMenuItem: MenuItem

    @Inject
    lateinit var contactRecyclerAdapter: ContactRecyclerAdapter

    private val contactListViewModel: ContactListViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_search_add, menu)

        //get search menu item
        val searchItem = menu.findItem(R.id.search_action).apply {
            setOnActionExpandListener(this@ContactListFragment)

        }

        //get search view and change background color
        val searchView = (searchItem.actionView as SearchView).apply {
            setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.search_view_color
                )
            )
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                contactRecyclerAdapter.filter.filter(newText)
                return false
            }

        })

        // get add contact menu item
        addContactMenuItem = menu.findItem(R.id.add_contact_item)

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

        toolbar = view.findViewById(R.id.contact_list_toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        binding.contactsRecyclerView.apply {
            adapter = contactRecyclerAdapter
            setDivider(R.drawable.recycler_divider)
        }

        contactListViewModel.getAllContacts().observe(viewLifecycleOwner, Observer {
            contactRecyclerAdapter.apply {
                submitList(it)
                contactListCopy = ArrayList(it)
            }
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search_action -> {
                startCircularRevealAnimation(toolbar, false)
                true
            }
            R.id.add_contact_item -> {
                findNavController().navigate(R.id.action_contactListFragment_to_contactEditFragment)
                true
            }
            else -> {
                Toast.makeText(requireContext(), "noitemSelected", Toast.LENGTH_LONG).show()
                return super.onOptionsItemSelected(item)
            }
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


    fun RecyclerView.setDivider(@DrawableRes drawableRes: Int) {
        val divider = DividerItemDecoration(
            this.context,
            DividerItemDecoration.VERTICAL
        )
        val drawable = ContextCompat.getDrawable(
            this.context,
            drawableRes
        )
        drawable?.let {
            divider.setDrawable(it)
            addItemDecoration(divider)
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

    override fun onClick(contact: Contact, view: View) {
        val action =
            ContactListFragmentDirections.actionContactListFragmentToContactDetailFragment(contact.contactId)
        view.findNavController().navigate(action)
    }
}