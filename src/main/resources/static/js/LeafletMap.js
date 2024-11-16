const attribution = 'Powered by <a href="https://www.geoapify.com/" target="_blank">Geoapify</a> | <a href="https://openmaptiles.org/" target="_blank">© OpenMapTiles</a> <a href="https://www.openstreetmap.org/copyright" target="_blank">© OpenStreetMap</a> contributors'
const tileLayerAttributes = {attribution: attribution, maxZoom: 20, id: 'osm-bright'};
const headers = {
    "X-Forwarded-For": "185.235.172.151",
}
const SHAPES = {
    RECTANGLE: 'rectangle',
    CIRCLE: 'circle',
    POINT: 'point'
};


class LeafletMap {
    constructor(mapContainer = "leaflet-map") {
        this.leaflet = L.map(mapContainer, {zoomSnap: 1, boxZoom: false});
        this.isRetina = L.Browser.retina;
        this.coordinates = [];
        this.zoom = 13
        this.layers = [];
        this.currentShapeType = SHAPES.RECTANGLE;
    }

    init() {
        this.leaflet.on("click", e => this.onClick.call(this, e))
        this.leaflet.on("zoomanim", e => this.onZoomAnim.call(this, e))
        L.tileLayer(`/map/frame/{z}/{x}/{y}?retina=${this.isRetina}`, tileLayerAttributes)
            .addTo(this.leaflet);
        this.currentPosition()
            .then(({location: {latitude, longitude}}) => {
                this.coordinates = [latitude, longitude]
                this.setView(this.coordinates);
                this.drawMarker(this.coordinates);

                this.obtainRectangleBounds([latitude, longitude])
                    .then(bounds => this.drawRectangle(bounds, this.zoom))
            });
    }

    drawMarker(coordinates = []) {
        if (this.currentShapeType !== SHAPES.RECTANGLE) {
            this.layers.push(L.marker(coordinates)
                .addTo(this.leaflet))
            this.setView(coordinates);
        }
    }

    removeLayer(layer) {
        if (layer) layer.remove()
    }

    clearLayers() {
        this.layers.forEach(this.removeLayer)
    }

    setView(coordinates = []) {
        this.leaflet.setView(coordinates, this.zoom);
    }

    onClick(event) {
        let coordinates = [event?.latlng?.lat, event?.latlng?.lng];
        this.clearLayers();
        if (this.currentShapeType === SHAPES.RECTANGLE) {
            this.obtainRectangleBounds(coordinates)
                .then(bounds => {
                    this.drawRectangle(bounds, this.zoom);
                    this.setView(coordinates);
                })
        } else if (this.currentShapeType === SHAPES.CIRCLE) {
            this.drawCircle(coordinates);
        } else {
            this.setView(coordinates);
            this.drawMarker(coordinates);
        }
    }

    onZoomAnim(event) {
        this.zoom = event?.zoom;
    }

    currentPosition() {
        return fetch("/ipinfo", {headers: headers})
            .then(response => response.json())
    }

    showCurrentPosition() {
        this.clearLayers();
        if (this.currentShapeType === SHAPES.RECTANGLE) {
            this.obtainRectangleBounds(this.coordinates)
                .then(bounds => this.drawRectangle(bounds, this.zoom))
        } else {
            this.setView(this.coordinates)
            this.setMarker(this.coordinates);
        }
    }

    showPlaces() {
        this.clearLayers()
        if (this.currentShapeType === SHAPES.RECTANGLE) {
            const {_northEast, _southWest} = this.leaflet.getBounds()
            const coordinates = `${_northEast.lat},${_northEast.lng},${_southWest.lat},${_southWest.lng}`

            this.obtainPlaces(this.currentShapeType, coordinates,)
        }
    }

    setShapeType(type) {
        if (type && SHAPES[type] !== this.currentShapeType) {
            this.currentShapeType = SHAPES[type]
            this.clearLayers();
        }
    }

    getShapeType() {
        return this.currentShapeType;
    }

    obtainBounds([latitude, longitude]) {
        return fetch("/address/reverse-geocoding", {
            method: "POST",
            body: JSON.stringify({latitude: latitude, longitude: longitude}),
            headers: {"Content-Type": "application/json"},
        }).then(res => res.json())
    }

    obtainRectangleBounds([latitude, longitude]) {
        return this.obtainBounds([latitude, longitude])
            .then(res => res.features?.[0]?.bbox)
    }

    obtainPlaces(type, coordinates, categories, filters, limit) {
        return fetch("/places/fetch", {
            method: "POST",
            body: JSON.stringify({type: type, coordinates: coordinates, categories: categories, limit: limit}),
            headers: {"Content-Type": "application/json"},
        })
    }

    async obtainPlaceCategories() {
        return fetch("places/categories")
            .then(response => response.json())
    }

    async obtainPlaceConditions() {
        return fetch("places/conditions")
            .then(response => response.json())
    }

    drawRectangle(bounds = [], offset = 1) {
        if (bounds.length === 4) {
            this.clearLayers()
            let attributes = {color: "#003cff", weight: 1}
            let localOffset = Math.abs((offset - 20 + offset) / 1000);
            let b = [[bounds[1], bounds[0]], [bounds[3] + localOffset, bounds[2] + localOffset]];
            this.layers.push(L.rectangle(b, attributes)
                .addTo(this.leaflet));
            this.leaflet.fitBounds(b);
        }
    }

    drawCircle(coordinates = [], radius = 2000) {
        if (coordinates.length === 2) {
            this.layers.push(L.circle(coordinates, {radius: radius * this.zoom})
                .addTo(this.leaflet))
            this.drawMarker(coordinates);
        }

    }

    create_option(value = "", callback, onclick) {
        let option = document.createElement("option");
        option.value = value;
        option.innerText = value;
        option.onclick = onclick;
        callback(option);
    }

    create_group(label = "") {
        let group = document.createElement("optgroup");
        group.label = label;
        return group;
    }

    on_click_dropdown_option(event, container) {
        let wrapper = document.createElement("div");
        let remove_button = document.createElement("input");
        let span = document.createElement("span");
        let target = event.target;

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

        container.appendChild(wrapper)
    }

    create_dropdown_filter(values = [], label = "", groupExtractor, callback) {
        const container = document.createElement("div");
        const selected_container = document.createElement("div");

        const labelEl = document.createElement("span");
        const selectEl = document.createElement("select");

        labelEl.innerText = label;
        labelEl.classList.add("label-dropdown-filter")
        selectEl.classList.add("form-select");
        selected_container.classList.add(label.toLowerCase());
        selected_container.id = label.toLowerCase();
        selected_container.disabled = true

        for (const value of values) {
            let group = this.create_group(groupExtractor(value))
            for (const filter of value.filters) {
                this.create_option(
                    filter,
                    option => group.appendChild(option),
                    e => this.on_click_dropdown_option(e, selected_container)
                );
            }
            selectEl.appendChild(group)
        }
        container.appendChild(labelEl);
        container.appendChild(selected_container);
        container.appendChild(selectEl);
        callback(container);
    }

    async addSearchData(type, container) {
        if (type && container) {
            if (SHAPES.RECTANGLE === type) {
                this.create_dropdown_filter(await this.obtainPlaceCategories(), "Categories",
                    (value) => value.description, (select) => container.appendChild(select))
                this.create_dropdown_filter(await this.obtainPlaceConditions(), "Conditions",
                    (value) => value.description, (select) => container.appendChild(select))
            }
        }
    }
}
