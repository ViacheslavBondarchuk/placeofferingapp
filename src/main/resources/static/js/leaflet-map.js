const attribution = 'Powered by <a href="https://www.geoapify.com/" target="_blank">Geoapify</a> | <a href="https://openmaptiles.org/" target="_blank">© OpenMapTiles</a> <a href="https://www.openstreetmap.org/copyright" target="_blank">© OpenStreetMap</a> contributors'
const tileLayerAttributes = {attribution: attribution, maxZoom: 20, id: 'osm-bright'};
const headers = {
    "X-Forwarded-For": "185.235.172.151",
}

class LeafletMap {
    constructor(mapContainer = "leaflet-map") {
        this.map = L.map(mapContainer, {zoomSnap: 1, boxZoom: false});
        this.isRetina = L.Browser.retina;
        this.coordinates = null;
        this.zoom = 13
        this.layers = [];
        this.rectangle_attributes = {color: "#003cff", weight: 1}
    }

    async init() {
        this.map.on("click", e => this.on_click.call(this, e))
        this.map.on("zoomanim", e => this.on_zoom_anim.call(this, e))
        L.tileLayer(`/map/frame/{z}/{x}/{y}?retina=${this.isRetina}`, tileLayerAttributes)
            .addTo(this.map);

        const {location: {latitude, longitude}} = await geoapify_data_fetcher.obtain_current_position()
        this.set_coordinates([latitude, longitude]);
        common_utils.call_function_by_for_rectangle_type(this, this.handle_click_for_rectangle_area, this.get_coordinates());
        common_utils.call_function_by_for_circle_type(this, this.handle_click_for_circle_area, this.get_coordinates());
        common_utils.call_function_by_for_point_type(this, this.handle_click_for_specifier_area, this.get_coordinates());
    }

    get_coordinates() {
        return this.coordinates;
    }

    set_coordinates(coordinates) {
        this.coordinates = coordinates;
    }

    draw_marker(coordinates = []) {
        this.layers.push(L.marker(coordinates)
            .addTo(this.map))
    }

    remove_layer(layer) {
        if (layer) {
            layer.remove()
        }
    }

    clear_layers() {
        this.layers.forEach(this.remove_layer)
    }

    set_view(coordinates = []) {
        this.map.setView(coordinates, this.zoom);
    }

    async handle_click_for_rectangle_area(scope, coordinates, setView = true) {
        const bounds = await geoapify_data_fetcher.obtain_rectangle_bounds(coordinates[0], coordinates[1]);
        scope.clear_layers();
        scope.draw_rectangle(bounds);
        if (setView) {
            scope.set_view(coordinates)
        }
    }

    async handle_click_for_circle_area(scope, coordinates, setView = true) {
        scope.clear_layers();
        scope.draw_circle(coordinates);
        scope.draw_marker(coordinates);
        if (setView) {
            scope.set_view(coordinates)
        }
    }

    async handle_click_for_specifier_area(scope, coordinates, setView = true) {
        scope.clear_layers();
        scope.draw_marker(coordinates);
        scope.set_view(coordinates);
        if (setView) {
            scope.set_view(coordinates)
        }
    }

    on_click(event) {
        const coordinates = [event?.latlng?.lat, event?.latlng?.lng];
        common_utils.call_function_by_for_rectangle_type(this, this.handle_click_for_rectangle_area, coordinates);
        common_utils.call_function_by_for_circle_type(this, this.handle_click_for_circle_area, coordinates);
        common_utils.call_function_by_for_point_type(this, this.handle_click_for_specifier_area, coordinates);
    }

    on_zoom_anim(event) {
        this.zoom = event?.zoom;
    }

    async show_current_position_for_rectangle_area_type(scope) {
        const bounds = await geoapify_data_fetcher.obtain_rectangle_bounds(scope.coordinates[0], scope.coordinates[1]);
        scope.clear_layers()
        scope.draw_rectangle(bounds)
        scope.set_view(scope.coordinates)
    }

    async show_current_position_for_circle_area_type(scope) {
        this.clear_layers()
    }

    async show_current_position_for_point_area_type(scope) {
        this.clear_layers()
    }


    show_current_position() {
        common_utils.call_function_by_for_rectangle_type(this, this.show_current_position_for_rectangle_area_type, this.coordinates);
        common_utils.call_function_by_for_circle_type(this, this.show_current_position_for_circle_area_type, this.coordinates);
        common_utils.call_function_by_for_point_type(this, this.show_current_position_for_point_area_type, this.coordinates);
    }

    show_places() {
        this.clear_layers()
        if (this.currentShapeType === SHAPES.RECTANGLE) {
            const {_northEast, _southWest} = this.map.getBounds()
            const coordinates = `${_northEast.lat},${_northEast.lng},${_southWest.lat},${_southWest.lng}`

            this.obtainPlaces(this.currentShapeType, coordinates,)
        }
    }

    draw_rectangle(bounds = []) {
        this.clear_layers()
        let complex_bounds = [[bounds[1], bounds[0]], [bounds[3], bounds[2]]];
        this.layers.push(L.rectangle(complex_bounds, this.rectangle_attributes)
            .addTo(this.map));
        this.map.fitBounds(complex_bounds);
    }

    draw_circle(coordinates = [], radius = 5000) {
        this.layers.push(L.circle(coordinates, {radius: radius})
            .addTo(this.map))
    }
}
