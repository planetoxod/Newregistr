package com.your_teachers.trafficrules.stuff.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.your_teachers.trafficrules.R
import com.your_teachers.trafficrules.objects.ItemType
import com.your_teachers.trafficrules.ui.botNavView.AllCategoryOfTests.stuff.AllCategoryOfTestsViewHolder
import com.your_teachers.trafficrules.ui.botNavView.Exams.stuff.ExamsViewHolder
import com.your_teachers.trafficrules.ui.botNavView.RoadSigns.stuff.RoadSignsViewHolder
import com.your_teachers.trafficrules.ui.botNavView.statTickets.stuff.StatTicketsContentViewHolder
import com.your_teachers.trafficrules.ui.botNavView.TrafficRulesTests.stuff.TrafficRulesTestsViewHolder
import com.your_teachers.trafficrules.ui.botNavView.TrainingMaps.stuff.TrainingMapsViewHolder
import com.your_teachers.trafficrules.ui.botNavView.favouritesSigns.stuff.TitleSignsViewHolder
import com.your_teachers.trafficrules.ui.botNavView.favouritesTickets.stuff.TitleTicketsViewHolder
import com.your_teachers.trafficrules.ui.botNavView.favouritesTraining.stuff.TitleTrainingViewHolder
import com.your_teachers.trafficrules.ui.botNavView.statExams.stuff.StatExamTitleViewHolder
import com.your_teachers.trafficrules.ui.botNavView.statExams.stuff.StatExamsContentViewHolder
import com.your_teachers.trafficrules.ui.botNavView.statSigns.stuff.StatSignsContentViewHolder
import com.your_teachers.trafficrules.ui.botNavView.statSigns.stuff.StatSignsTitleViewHolder
import com.your_teachers.trafficrules.ui.botNavView.statTickets.stuff.StatTicketsTitleViewHolder
import com.your_teachers.trafficrules.ui.botNavView.statTraining.stuff.StatTrainingContentViewHolder
import com.your_teachers.trafficrules.ui.botNavView.statTraining.stuff.StatTrainingTitleViewHolder
import com.your_teachers.trafficrules.ui.bottomSheets.Countries.stuff.CountriesViewHolder
import java.util.ArrayList

class CustomAdapter<T : ItemType>(
        private val customRecyclerClickListener: CustomRecyclerClickListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items = ArrayList<T>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View
        return when (viewType) {
            ItemType.TYPE_TITLE_TRAINING -> {
                itemView = LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.card_stats_ticket_title, parent, false)
                StatTrainingTitleViewHolder(itemView, customRecyclerClickListener)
            }
            ItemType.TYPE_TITLE_SIGNS -> {
                itemView = LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.card_stats_ticket_title, parent, false)
                StatSignsTitleViewHolder(itemView, customRecyclerClickListener)
            }
            ItemType.TYPE_TITLE_EXAM -> {
                itemView = LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.card_stats_exam_title, parent, false)
                StatExamTitleViewHolder(itemView, customRecyclerClickListener)
            }
            ItemType.TYPE_TITLE_TICKETS -> {
                itemView = LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.card_stats_ticket_title, parent, false)
                StatTicketsTitleViewHolder(itemView, customRecyclerClickListener)
            }
            ItemType.TYPE_COUNTRY -> {
                itemView = LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.card_country, parent, false)
                CountriesViewHolder(itemView, customRecyclerClickListener)
            }
            ItemType.TYPE_ALLCATEGORYOFTESTS -> {
                itemView = LayoutInflater
                        .from(parent.context)
                       // .inflate(R.layout.card_all_category_of_tests, parent, false)
                        .inflate(R.layout.all_tests_exams, parent, false)
                AllCategoryOfTestsViewHolder(itemView, customRecyclerClickListener)
            }
            ItemType.TYPE_CATEGORYOFEXAMS -> {
                itemView = LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.card_exams, parent, false)
                ExamsViewHolder(itemView, customRecyclerClickListener)
            }
            ItemType.TYPE_TRAFFICRULESTEST -> {
                itemView = LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.card_traffic_rules_tests, parent, false)
                TrafficRulesTestsViewHolder(itemView, customRecyclerClickListener)
            }
            ItemType.TYPE_ROADSIGNS -> {
                itemView = LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.card_road_signs, parent, false)
                RoadSignsViewHolder(itemView, customRecyclerClickListener)
            }
            ItemType.TYPE_TRAININGMAPS -> {
                itemView = LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.card_training_maps, parent, false)
                TrainingMapsViewHolder(itemView, customRecyclerClickListener)
            }
            ItemType.TYPE_STATTICKETS -> {
                itemView = LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.card_stats_content, parent, false)
                StatTicketsContentViewHolder(itemView, customRecyclerClickListener)
            }
            ItemType.TYPE_FAVOURITES_TICKETS -> {
                itemView = LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.card_stats_ticket_title, parent, false)
                TitleTicketsViewHolder(itemView, customRecyclerClickListener)
            }
            ItemType.TYPE_FAVOURITES_SIGNS -> {
                itemView = LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.card_stats_ticket_title, parent, false)
                TitleSignsViewHolder(itemView, customRecyclerClickListener)
            }
            ItemType.TYPE_FAVOURITES_TRAINING -> {
                itemView = LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.card_stats_ticket_title, parent, false)
                TitleTrainingViewHolder(itemView, customRecyclerClickListener)
            }
            ItemType.TYPE_STATEXAMS -> {
                itemView = LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.card_stats_exam_content, parent, false)
                StatExamsContentViewHolder(itemView, customRecyclerClickListener)
            }
            ItemType.TYPE_STATSIGNS -> {
                itemView = LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.card_stats_content, parent, false)
                StatSignsContentViewHolder(itemView, customRecyclerClickListener)
            }
            ItemType.TYPE_STATTRAINING -> {
                itemView = LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.card_stats_content, parent, false)
                StatTrainingContentViewHolder(itemView, customRecyclerClickListener)
            }
            else -> TODO()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //Заполнение данными
        when (getItemViewType(position)) {
            ItemType.TYPE_TITLE_TRAINING -> (holder as StatTrainingTitleViewHolder).bindView(position, items)
            ItemType.TYPE_TITLE_SIGNS -> (holder as StatSignsTitleViewHolder).bindView(position, items)
            ItemType.TYPE_TITLE_EXAM -> (holder as StatExamTitleViewHolder).bindView(position, items)
            ItemType.TYPE_TITLE_TICKETS -> (holder as StatTicketsTitleViewHolder).bindView(position, items)
            ItemType.TYPE_STATTICKETS -> (holder as StatTicketsContentViewHolder).bindView(position, items)
            ItemType.TYPE_FAVOURITES_TICKETS -> (holder as TitleTicketsViewHolder).bindView(position, items)
            ItemType.TYPE_FAVOURITES_SIGNS -> (holder as TitleSignsViewHolder).bindView(position, items)
            ItemType.TYPE_FAVOURITES_TRAINING -> (holder as TitleTrainingViewHolder).bindView(position, items)
            ItemType.TYPE_STATEXAMS -> (holder as StatExamsContentViewHolder).bindView(position, items)
            ItemType.TYPE_STATSIGNS -> (holder as StatSignsContentViewHolder).bindView(position, items)
            ItemType.TYPE_STATTRAINING -> (holder as StatTrainingContentViewHolder).bindView(position, items)
            ItemType.TYPE_COUNTRY -> (holder as CountriesViewHolder).bindView(position, items)
            ItemType.TYPE_ALLCATEGORYOFTESTS -> (holder as AllCategoryOfTestsViewHolder).bindView(position, items)
            ItemType.TYPE_CATEGORYOFEXAMS -> (holder as ExamsViewHolder).bindView(position, items)
            ItemType.TYPE_TRAFFICRULESTEST -> (holder as TrafficRulesTestsViewHolder).bindView(position, items)
            ItemType.TYPE_ROADSIGNS -> (holder as RoadSignsViewHolder).bindView(position, items)
            ItemType.TYPE_TRAININGMAPS -> (holder as TrainingMapsViewHolder).bindView(position, items)
        }
    }

    fun setItems(objectsList: ArrayList<T>) {
        this.items = objectsList
        notifyDataSetChanged()
    }

    fun getItems(): ArrayList<out ItemType> = items

    fun getItem(id: Int) : T = items[id]

    override fun getItemViewType(position: Int): Int = items[position].itemType

    override fun getItemCount(): Int = items.size

}