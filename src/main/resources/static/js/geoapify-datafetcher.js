var geoapify_data_fetcher = (function () {

    this.obtain_bounds = async function (latitude, longitude) {
        return await fetch("/address/reverse-geocoding", {
            method: "POST",
            body: JSON.stringify({latitude: latitude, longitude: longitude}),
            headers: {"Content-Type": "application/json"},
        }).then(res => res.json())
    }

    this.obtain_rectangle_bounds = async function (latitude, longitude) {
        return await this.obtain_bounds(latitude, longitude)
            .then(res => res.features?.[0]?.bbox)
    }

    this.obtain_places = async function (type, coordinates, categories, filters, limit) {
        return await fetch("/places/fetch", {
            method: "POST",
            body: JSON.stringify({type: type, coordinates: coordinates, categories: categories, limit: limit}),
            headers: {"Content-Type": "application/json"},
        })
    }

    this.obtain_places_categories = async function () {
        return await fetch("places/categories")
            .then(response => response.json())
    }

    this.obtain_places_conditions = async function () {
        return fetch("places/conditions")
            .then(response => response.json())
    }

    this.obtain_current_position = async function () {
        const response = await fetch("/ipinfo", {headers: headers});
        return await response.json()
    }

    return this;
}());
