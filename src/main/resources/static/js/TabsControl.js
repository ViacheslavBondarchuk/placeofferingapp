const activeClassName = "active"
const tupleIndexAttributeName = "tupleIndex"
const hiddenClassName = "hidden"
const shapeTypeAttributeName = "shape-type"

class TabsControl {
    constructor(tabSelector = "", buttonSelector = "button") {
        this.tabs = document.querySelectorAll(tabSelector);
        this.buttons = document.querySelectorAll(buttonSelector);
        this.pairs = [];
        this.init()
    }

    init() {
        if (this.tabs.length === this.buttons.length) {
            for (let i = 0; i < this.buttons.length; i++) {
                if (i === 0) {
                    const tab = this.tabs[i];
                    this.showTab(tab)
                    leafletMap.addSearchData(leafletMap.getShapeType(), tab.children[0])
                }
                this.tabs[i].setAttribute(tupleIndexAttributeName, i.toString())
                this.buttons[i].setAttribute(tupleIndexAttributeName, i.toString())
                this.addOnClickListener(this.buttons[i])
                this.pairs.push([this.tabs[i], this.buttons[i]]);
            }
        }
    }

    addOnClickListener(tab) {
        tab.addEventListener("click", e => this.onClick.call(this, e))
    }

    hideTab(element) {
        element.classList.add(hiddenClassName);
        element.classList.remove(activeClassName)
    }

    showTab(element) {
        element.classList.remove(hiddenClassName);
        element.classList.add(activeClassName)
    }

    onClick(event) {
        let element = event.target;
        leafletMap.setShapeType(element.getAttribute(shapeTypeAttributeName));
        for (let tab of this.tabs) {
            const attribute = element.getAttribute(tupleIndexAttributeName);
            if (tab.getAttribute(tupleIndexAttributeName) === attribute) {
                this.showTab(tab)
                leafletMap.addSearchData(attribute, element.children[0])
            } else {
                this.hideTab(tab)
            }
        }
    }
}
