
export enum CounterReducerActionType {
    INCREMENT = 'COUNTER_INCREMENT',
}

export interface ICounterState {
    count: number
}

const initState: ICounterState = {
    count: 0,
}

const CounterReducer = (state = initState, action: any): any => {
    switch (action.type) {
        case CounterReducerActionType.INCREMENT:
        {
            return {
                count: state.count + 1,
            }
        }
    }
    return state;
}

export default CounterReducer;