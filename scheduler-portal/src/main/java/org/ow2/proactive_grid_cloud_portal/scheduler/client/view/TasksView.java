/*
 * ProActive Parallel Suite(TM):
 * The Open Source library for parallel and distributed
 * Workflows & Scheduling, Orchestration, Cloud Automation
 * and Big Data Analysis on Enterprise Grids & Clouds.
 *
 * Copyright (c) 2007 - 2017 ActiveEon
 * Contact: contact@activeeon.com
 *
 * This library is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation: version 3 of
 * the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * If needed, contact us to obtain a release under GPL Version 2 or 3
 * or a different license than the AGPL.
 */
package org.ow2.proactive_grid_cloud_portal.scheduler.client.view;

import java.util.List;

import org.ow2.proactive_grid_cloud_portal.scheduler.client.SchedulerListeners.TasksUpdatedListener;
import org.ow2.proactive_grid_cloud_portal.scheduler.client.Task;
import org.ow2.proactive_grid_cloud_portal.scheduler.client.controller.PaginationController;
import org.ow2.proactive_grid_cloud_portal.scheduler.client.controller.TasksController;
import org.ow2.proactive_grid_cloud_portal.scheduler.client.view.grid.tasks.ExpandTasksColumnsFactory;
import org.ow2.proactive_grid_cloud_portal.scheduler.client.view.grid.tasks.ExpandableTasksColumnsFactory;
import org.ow2.proactive_grid_cloud_portal.scheduler.client.view.grid.tasks.ExpandableTasksListGrid;

import com.smartgwt.client.widgets.layout.Layout;


/**
 * Contains the ListGrid that displays tasks
 *
 * @author mschnoor
 */
public class TasksView extends AbstractGridItemsView<Task> implements TasksUpdatedListener {

    protected TasksController controller;

    public TasksView(TasksController controller) {
        this.controller = controller;
        this.controller.getModel().addTasksUpdatedListener(this);
        this.itemName = "tasks";
    }

    public void tasksUpdating() {
        this.itemUpdating();
    }

    public void tasksUpdatedFailure(String message) {
        this.itemUpdatedFailure(message);
    }

    public void tasksUpdated(List<Task> tasks, long totalTasks) {
        this.itemUpdated();
    }

    protected Layout buildToolbar() {
        return this.controller.getTaskNavigationController().buildView();
    }

    protected Layout buildPagination() {
        PaginationController<?> paginationController = this.controller.getTaskNavigationController()
                                                                      .getPaginationController();
        return buildPagination(paginationController);
    }

    protected void buildGrid() {
        ExpandableTasksColumnsFactory expandableFactory = new ExpandableTasksColumnsFactory();
        ExpandTasksColumnsFactory expandFactory = new ExpandTasksColumnsFactory();
        this.itemsGrid = new ExpandableTasksListGrid(this.controller, expandableFactory, expandFactory, "tasksDS_");
        this.itemsGrid.build();
    }

    @Override
    public void pageChanged() {
        this.controller.selectTask(null);
    }

    @Override
    public void totalItemChanged() {

    }

}
