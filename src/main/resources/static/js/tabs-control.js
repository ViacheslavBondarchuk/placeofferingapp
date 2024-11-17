const tupleIndexAttributeName = "tupleIndex"
const shapeTypeAttributeName = "shape-type"

class TabsControl {
    constructor(tabSelector = "", buttonSelector = "button") {
        this.tabs = document.querySelectorAll(tabSelector);
        this.buttons = document.querySelectorAll(buttonSelector);
        this.categories = [];
        this.conditions = []
        this.pairs = [];
    }

    async init() {
        this.categories = await geoapify_data_fetcher.obtain_places_categories()
        this.conditions = await geoapify_data_fetcher.obtain_places_conditions()

        this.fill_categories_selection()
        this.fill_conditions_selection()

        if (this.tabs.length === this.buttons.length) {
            for (let i = 0; i < this.buttons.length; i++) {
                this.tabs[i].setAttribute(tupleIndexAttributeName, i.toString())
                this.buttons[i].setAttribute(tupleIndexAttributeName, i.toString())
                this.add_on_click_listener_for_button(this.buttons[i])
                this.pairs.push([this.tabs[i], this.buttons[i]]);
                if (i === 0) {
                    const tab = this.tabs[i];
                    this.show_rectangle_controls(this, tab)
                    common_utils.set_shape_type(this.get_tab_shape_type(tab))
                }
            }
        }
    }

    fill_categories_selection() {
        const select = document.querySelector("#categories-select");
        const selected_container = document.querySelector("#categories-selected-container");
        this.fill_selection(select, selected_container, this.categories)
    }

    fill_conditions_selection() {
        const select = document.querySelector("#condition-select");
        const selected_container = document.querySelector("#conditions-selected-container");
        this.fill_selection(select, selected_container, this.conditions)
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

    show_rectangle_controls(scope, container) {
        common_utils.show_element(tab)
        common_utils.add_active_class(tab)

        // await leaflet_map.handle_click_for_rectangle_area(leaflet_map, leaflet_map.get_coordinates());
        // tabs_controls.
    }

    show_circle_controls(scope) {

    }

    show_point_controls(scope) {

    }

    add_on_click_listener_for_button(button) {
        button.addEventListener("click", e => this.on_click.call(this, e))
    }

    on_click(event) {
        let element = event.target;
        common_utils.set_shape_type(element.getAttribute(shapeTypeAttributeName));
        for (let tab of this.tabs) {
            const attribute = element.getAttribute(tupleIndexAttributeName);
            if (tab.getAttribute(tupleIndexAttributeName) === attribute) {
                common_utils.show_element(tab)
                common_utils.add_active_class(tab)
                // leafletMap.addSearchData(attribute, element.children[0])

                let container = element.children[0];
                common_utils.call_function_by_for_rectangle_type(this, this.add_rectangle_controls_data_to_leaf_let_map, container)
                common_utils.call_function_by_for_circle_type(this, this.add_circle_controls_data_to_leaf_let_map, container)
                common_utils.call_function_by_for_point_type(this, this.add_point_controls_data_to_leaf_let_map, container)
            } else {
                common_utils.hide_element(tab)
                common_utils.remove_active_class(tab)
            }
        }
    }
}
