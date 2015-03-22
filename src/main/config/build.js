/**
 * This configuration optimizes the raw JavaScript files in src/main/webapp/js
 * and puts the concatenated/uglified files in target/classes/public/js, where they can
 * be packaged together with the application.
 *
 * Any CSS files found in src/main/webapp are also copied over and optimized
 * (although we have a special Compass production config that already takes care of that).
 */
({
    appDir: '../webapp',
    baseUrl: 'js',
    paths: {
        app: 'app',

        // ignore libraries that are fetched from webjars
        jquery: 'empty:'
    },

    // output directory - Spring Boot serves files under "public" automatically
    dir: '../../../target/classes/public',

    // each module will result in one concatenated file
    modules: [
        {
            name: 'main',
            include: []
        },

        {
            name: 'form',
            include: ['app/utils/util', 'app/form'],
            exclude: ['main']
        }
    ]
})
