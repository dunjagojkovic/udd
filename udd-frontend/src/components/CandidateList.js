import Candidate from "./Candidate";

const CandidateList = ({candidates}) => {
    
    return(
        <section className="our-webcoderskull padding-lg">
            <ul className="row">
            {candidates.length == 0 && <h3 style={{textAlign: "center"}}>Nema rezultata.</h3>}
            {
            (candidates).map((candidate, index) => {
                return (
                    <Candidate key={index} candidate={candidate} />
                );
            })}
            </ul>
        </section>
    );
}

export default CandidateList;