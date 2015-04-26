/**
 * This configuration optimizes the raw JavaScript files in src/main/webapp/js
 * and puts the concatenated/uglified files in target/classes/public/js, where they can
 * be packaged together with the application.
 *
 * Any CSS files found in src/main/webapp are also copied over and optimized
 * (although we have a special Compass production config that already takes care of that).
 */
({
    appDir: '../resources/public',
    baseUrl: 'js',
    paths: {
        app: 'app'
    },

    dir: '../../../target/classes/public',

    // each module will result in one concatenated file
    modules: [
        {
            name: 'main',
            include: []
        },

        {
            name: 'form',
            include: ['app/form'],
            exclude: ['main']
        },

        {
            name: 'pay',
            include: ['app/pay'],
            exclude: ['main']
        }

    ]
})
