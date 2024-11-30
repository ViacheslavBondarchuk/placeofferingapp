const tupleIndexAttributeName = "tupleIndex"
const shapeTypeAttributeName = "shape-type"

class TabsControl {
    constructor(tabSelector = "", buttonSelector = "button") {
        this.tab = document.querySelector(tabSelector);
        this.buttons = document.querySelectorAll(buttonSelector);
        this.categories_select = document.querySelector("#categories-select");
        this.conditions_select = document.querySelector("#condition-select");
        this.categories_selected_container = document.querySelector("#categories-selected-container");
        this.conditions_selected_container = document.querySelector("#conditions-selected-container");
        this.categories_dropdown = document.querySelector("#categories-dropdown")
        this.conditions_dropdown = document.querySelector("#conditions-dropdown");
        this.radius_group = document.querySelector("#radius-group");
        this.search_group = document.querySelector("#search-group");
        this.categories = [];
        this.conditions = []
    }

    async init() {
        this.categories = await geoapify_data_fetcher.obtain_places_categories()
        this.conditions = await geoapify_data_fetcher.obtain_places_conditions()

        this.fill_categories_selection()
        this.fill_conditions_selection()

        for (let i = 0; i < this.buttons.length; i++) {
            this.buttons[i].setAttribute(tupleIndexAttributeName, i.toString())
            this.add_on_click_listener_for_button(this.buttons[i])
            if (i === 0) {
                common_utils.set_shape_type(this.get_tab_shape_type(this.buttons[i]));
                this.show_active_tab(this.buttons[i]);
                this.show_rectangle_controls(this)
            }
        }
    }

    fill_categories_selection() {
        this.fill_selection(this.categories_select, this.categories_selected_container, this.categories)
    }

    fill_conditions_selection() {
        this.fill_selection(this.conditions_select, this.conditions_selected_container, this.conditions)
    }

    fill_selection(select, selected_container, values = []) {
        for (const {icon, filters, description} of values) {
            let group = common_utils.create_option_group(description);
            for (const filter of filters) {
                const option = common_utils.create_option(filter, e => this.on_option_click(e, selected_container));
                group.appendChild(option)
            }
            select.appendChild(group);
        }
    }

    on_option_click(event, selected_container) {
        event.stopPropagation();

        const wrapper = document.createElement("div");
        const remove_button = document.createElement("input");
        const span = document.createElement("span");
        const target = event.target;

        target.hidden = true

        span.innerText = target.value;
        span.classList.add("selected-el-label")

        remove_button.type = "button";
        remove_button.value = "x";
        remove_button.classList.add("remove-selected")
        remove_button.onclick = (e) => {
            wrapper.remove();
            target.hidden = false;
        }

        wrapper.appendChild(span);
        wrapper.appendChild(remove_button);
        wrapper.classList.add("selected-el-wrapper")

        selected_container.appendChild(wrapper)
    }

    get_tab_shape_type(tab) {
        return tab.getAttribute(shapeTypeAttributeName)
    }

    show_rectangle_controls(scope) {
        common_utils.show_element(scope.conditions_dropdown);
        common_utils.show_element(scope.categories_dropdown);
        common_utils.hide_element(scope.search_group);
        common_utils.hide_element(scope.radius_group);
    }

    show_circle_controls(scope) {
        common_utils.show_element(scope.radius_group);
        common_utils.show_element(scope.search_group);
        common_utils.show_element(scope.conditions_dropdown);
        common_utils.show_element(scope.categories_dropdown);

    }

    show_point_controls(scope) {
        common_utils.show_element(scope.conditions_dropdown);
        common_utils.show_element(scope.categories_dropdown);
        common_utils.show_element(scope.search_group);
        common_utils.hide_element(scope.radius_group);
    }

    add_on_click_listener_for_button(button) {
        button.addEventListener("click", e => this.on_click.call(this, e))
    }

    show_active_tab(clicked_button) {
        common_utils.add_active_class(clicked_button);
        for (const button of this.buttons) {
            if (clicked_button !== button) {
                common_utils.remove_active_class(button)
            }
        }
    }

    on_click(event) {
        event.stopPropagation();
        let button = event.target;
        common_utils.set_shape_type(button.getAttribute(shapeTypeAttributeName));
        this.show_active_tab(button);
        common_utils.call_function_by_for_rectangle_type(this, this.show_rectangle_controls, this);
        common_utils.call_function_by_for_circle_type(this, this.show_circle_controls, this);
        common_utils.call_function_by_for_point_type(this, this.show_point_controls, this);

    }
}
