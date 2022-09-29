

// add magnifying glass to submit
    const SearchBar = ({posts, searchResults}) => {
        const handleSubmit = (e) = e.preventDefault()
        const handleSearchChange = (e) =>{
          if(!e.target.value) return setSearchResults(posts)
  
          const resultsArray = posts.filter(post => post.title.includes(e.target.value) || post.body.includes(e.target.value))
  
          setSearchResults(resultsArray)
        }
  
      return(
          <>
              <h2>Brewery Search</h2><p>Find Specific Breweriees by Name, City, or State.</p>
              <div><form className="SearchBar" onSubmit={handleSubmit}>
                <input className="search_input"
                input="text" id="search" onChange={handleSearchChange}/>
                
                <button className="search_button">Search</button>
                </form>
                </div>
  
  
  
          </>
      )
  }
  
  export default SearchBar;