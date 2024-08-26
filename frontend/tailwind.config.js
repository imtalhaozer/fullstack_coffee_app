/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      fontFamily:{
        caveat:["Caveat","sans-serif"]
      },
      screens:{
        sm:"200px",
        md:"400px",
        lg:"800px",
        xl:"1600px",
        xxl:"3200px",
      },
      colors:{
        primary:'#FFF9AE',
        secondary:"#FFDE55",
        third:"#DDCFE8",
        fourth:"#9AE9ED"
      }
    },
  },
  plugins: [],
}