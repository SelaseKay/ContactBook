package com.judekwashie.contactbook

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.judekwashie.contactbook.databinding.FragmentContactDetailBinding
import kotlinx.android.synthetic.main.collapsing_toolbar.*
import kotlinx.android.synthetic.main.fragment_contact_detail.*

class ContactDetailFragment : Fragment(),
    androidx.appcompat.widget.Toolbar.OnMenuItemClickListener {

    private var _binding: FragmentContactDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_toolbar_items, menu)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentContactDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.collapsingToolbar.toolbar.apply {
            (activity as AppCompatActivity).setSupportActionBar(binding.collapsingToolbar.toolbar)
            setOnMenuItemClickListener(this@ContactDetailFragment)
        }

    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.edit_item -> {
                Toast.makeText(this.requireContext(), "edit clicked", Toast.LENGTH_LONG).show()
                true
            }
            R.id.add_photo_item ->{
                Toast.makeText(this.requireContext(), "add photo clicked", Toast.LENGTH_LONG).show()
                true
            }
            else -> {
                false
            }
        }
    }


}