import { create } from "zustand";

interface useCountType {
  count: number;
}
interface useCountState {
  countState: useCountType;
}
const defaultState = { count: 0 };

export const useCountStore = create<useCountState & any>((set: any) => ({
  countState: defaultState,
  plusCount() {
    set((state: any) => ({
      countState: { count: state.countState.count + 1 },
    }));
  },
  initCount() {
    set(() => ({
      countState: defaultState,
    }));
  },
}));
