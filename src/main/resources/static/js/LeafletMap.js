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
        this.previousLayer = null;
        this.currentShapeType = SHAPES.RECTANGLE;
        this.previouseRectangle = null
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
                this.setMarker(this.coordinates);

                this.obtainBounds([latitude, longitude])
                    .then(bounds => this.setRectangleArea(bounds, this.zoom))
            });
    }

    setMarker(coordinates = []) {
        if (this.currentShapeType !== SHAPES.RECTANGLE) {
            this.previousLayer = L.marker(coordinates)
                .addTo(this.leaflet);
        }
    }

    removeShape(layer) {
        if (layer) layer.remove()
    }

    setView(coordinates = []) {
        this.leaflet.setView(coordinates, this.zoom);
    }

    onClick(event) {
        let coordinates = [event?.latlng?.lat, event?.latlng?.lng];
        this.removeShape(this.previousLayer);
        if (this.currentShapeType === SHAPES.RECTANGLE) {
            this.obtainBounds(coordinates)
                .then(bounds => {
                    this.setRectangleArea(bounds, this.zoom);
                    this.setView(coordinates);
                })
        } else {
            this.setView(coordinates);
            this.setMarker(coordinates);
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
        this.removeShape(this.previousLayer);
        if (this.currentShapeType === SHAPES.RECTANGLE) {
            this.obtainBounds(this.coordinates)
                .then(bounds => this.setRectangleArea(bounds, this.zoom))
        } else {
            this.setView(this.coordinates)
            this.setMarker(this.coordinates);
        }
    }

    obtainBounds([latitude, longitude]) {
        return fetch("/address/reverse-geocoding", {
            method: "POST",
            body: JSON.stringify({latitude: latitude, longitude: longitude}),
            headers: {"Content-Type": "application/json"},
        }).then(res => res.json())
            .then(res => res.features?.[0]?.bbox)
    }

    setRectangleArea(bounds = [], offset = 1) {
        if (bounds.length === 4) {
            this.removeShape(this.previouseRectangle)
            let attributes = {color: "#003cff", weight: 1}
            let localOffset = Math.abs((offset - 20 + offset) / 1000);
            let b = [[bounds[1], bounds[0]], [bounds[3] + localOffset, bounds[2] + localOffset]];
            this.previouseRectangle = L.rectangle(b, attributes)
                .addTo(this.leaflet);
            this.leaflet.fitBounds(b);
        }

    }
}
