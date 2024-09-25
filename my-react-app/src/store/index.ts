import {combineReducers} from "redux";
import {configureStore} from "@reduxjs/toolkit";
import counterReducer from "./reducers/CounterReducer.ts";
import {thunk} from "redux-thunk";

export const rootReducer = combineReducers({
    counter: counterReducer,
});

//console.log("process.env.NODE_ENV", process.env.NODE_ENV);

export const store = configureStore({
    reducer: rootReducer,
    devTools: process.env.NODE_ENV !== 'production',
    middleware: (getDefaultMiddleware) => getDefaultMiddleware().concat(thunk),
    // middleware: [thunk]
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;