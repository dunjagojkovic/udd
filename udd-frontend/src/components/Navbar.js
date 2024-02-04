const Navbar = () => {
    return (
        <div className="m-4 p-1">
        <ul className="nav nav-pills nav-fill ">
            <li className="nav-item" key={1}>
                <a style={{textDecoration: "none"}} href="/"><h1>UDD</h1></a>
            </li>
            <li className="nav-item" key={2}>
                <a className="nav-link active" aria-current="page" href="/">Pretraga</a>
            </li>
            <li className="nav-item" key={3}>
                <a className="nav-link" href="/combinedSearch">Kombinovana pretraga</a>
            </li>
            <li className="nav-item" key={4}>
                <a className="nav-link" href="/registration">Registracija</a>
            </li>
        </ul>
        <hr></hr>
    </div>
    );
}

export default Navbar;