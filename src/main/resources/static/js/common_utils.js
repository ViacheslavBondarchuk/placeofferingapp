const SHAPES = {
    RECTANGLE: 'rectangle',
    CIRCLE: 'circle',
    POINT: 'point'
};
const DEFAULT_SHAPE_TYPE = SHAPES.RECTANGLE;

var common_utils = (function () {
    const hiddenClassName = "hidden"
    const activeClassName = "active"


    this.call_function_by_for_rectangle_type = function (scope, callback, ...args) {
        if (this.get_shape_type() === SHAPES.RECTANGLE) {
            callback(scope, ...args);
        }
    }

    this.call_function_by_for_circle_type = function (scope, callback, ...args) {
        if (this.get_shape_type() === SHAPES.CIRCLE) {
            callback(scope, ...args);
        }
    }

    this.call_function_by_for_point_type = function (scope, callback, ...args) {
        if (this.get_shape_type() === SHAPES.POINT) {
            callback(scope, ...args);
        }
    }

    this.get_shape_type = function () {
        return this.shape_type || DEFAULT_SHAPE_TYPE;
    }

    this.set_shape_type = function (type) {
        this.shape_type = type;
    }

    this.hide_element = function (element) {
        if (element) {
            element.classList.add(hiddenClassName);
        }
    }

    this.show_element = function (element) {
        if (element) {
            element.classList.remove(hiddenClassName);
        }
    }

    this.add_classes = function (element, classes = []) {
        if (element && classes) {
            element.classList.add(...classes);
        }
    }

    this.remove_classes = function (element, classes = []) {
        if (element && classes) {
            element.classList.remove(...classes);
        }
    }

    this.add_active_class = function (element) {
        this.add_classes(element, [activeClassName])
    }

    this.remove_active_class = function (element) {
        this.remove_classes(element, [activeClassName])
    }

    this.create_option_group = function (label) {
        const group = document.createElement("optgroup");
        group.label = label;
        return group;
    }

    this.create_option = function (value, on_click) {
        const option = document.createElement("option");
        option.value = value;
        option.innerText = value;
        option.onclick = on_click;
        return option;
    }

    return this;
}())
