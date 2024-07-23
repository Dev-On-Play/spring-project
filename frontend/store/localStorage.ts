const setItem = ({ key, item }: { key: string; item: string }) => {
  if (typeof window !== "undefined") {
    localStorage.setItem(key, item)
  }
}
const getItem = ({ key }: { key: string }) => {
  if (typeof window !== "undefined") {
    return localStorage.getItem(key)
  }
  return null
}
const removeItem = ({ key }: { key: string }) => {
  if (typeof window !== "undefined") {
    localStorage.removeItem(key)
  }
}
export { setItem, getItem, removeItem }
