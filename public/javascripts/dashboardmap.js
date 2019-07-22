//dropdown example
new Maplace({
    show_markers: false,
    locations: [{
        lat: 45.9,
        lon: 10.9,
        zoom: 8
    }]
}).Load();

//or

new Maplace({
    map_options: {
        set_center: [45.9, 10.9],
        zoom: 8
    }
}).Load();