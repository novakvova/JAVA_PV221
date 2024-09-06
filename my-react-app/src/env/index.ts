const SERVER_HOST: string = import.meta.env.VITE_APP_SERVER_HOST;
const CATEGORIES_API_URL: string = import.meta.env.VITE_APP_CATEGORIES_API_URL;
const IMAGES_FOLDER: string = import.meta.env.VITE_APP_IMAGES_FOLDER;

const APP_ENV = {
    SERVER_HOST,
    CATEGORIES_API_URL,
    IMAGES_FOLDER
};

export {APP_ENV}