// apiClient.ts
import axios, { AxiosResponse } from "axios"

// Define the shape of the data you expect from the API
interface MogakosData {
  // Example properties - replace with actual properties from your API
  id: number
  profileImageUrl: string
  nickname: string
  title: string
  date: string
  category: string
  minPeople: number
  currentPeople: number
  maxPeople: number
  hashtags: string[]
  content: string
}

// Create an instance of axios with the base URL
const apiClient = axios.create({
  baseURL: "http://localhost:8080",
  headers: {
    "Content-Type": "application/json",
  },
})

// Function to fetch data from /api/mogakos
async function fetchMogakosData(): Promise<MogakosData[]> {
  try {
    const response: AxiosResponse<MogakosData[]> =
      await apiClient.get("/api/mogakos")
    return response.data
  } catch (error) {
    console.error("Error fetching data from /api/mogakos:", error)
    throw error
  }
}

// Example usage of the fetch function
fetchMogakosData()
  .then((data) => {
    // Show first 10 items in the console
    console.log("Fetched data:", data.slice(0, 10))
  })
  .catch((error) => {
    console.error("Error:", error)
  })

export default fetchMogakosData
