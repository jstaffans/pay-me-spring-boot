/**
 * This configuration optimizes the raw JavaScript files in src/main/resources/public/js
 * and puts the concatenated/uglified files in target/classes/public/js, where they can
 * be packaged together with the application.
 */
({
    appDir: '../resources/public/js',
    baseUrl: '.',
    paths: {
        app: 'app',

        // ignore libraries that are fetched from webjars
        jquery: 'empty:'
    },

    // output directory
    dir: '../../../target/classes/public/js',

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
