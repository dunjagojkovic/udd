import { useState } from "react";
import Swal from "sweetalert2";

import axios from 'axios';
import CandidateList from "./CandidateList";

const Search = () => {
    const [category, setCategory] = useState("");
    const [text, setText] = useState("");
    const [degree, setDegree] = useState("");
    const [phraseQuery, setPhraseQuery] = useState(false);
    const [isPending, setIsPending] = useState(false);
    const [candidates, setCandidates] = useState([]);

    const Validate = () => {
        if(category!="" && category=="education"){
            if(degree=="")
                return true;
            return false;
        }else if(category!="" && category!="education"){
            if(text=="")
                return true;
            return false;
        }
        return true;
    }

    const onSubmit = async (e) => {
        e.preventDefault();

        setIsPending(true);
        const dto = {
            "category": category,
            "content": (category == "education" ? degree : text),
            "phraseQuery": phraseQuery,
        };

        axios.post(axios.defaults.baseURL + 'search', dto)
            .then(res => {
                if (res.data.error) {
                    setIsPending(false);
                    Swal.fire({
                        icon: 'error',
                        title: 'Oops...',
                        text: res.data.error,
                    });
                } else {
                    setIsPending(false);
                    Swal.fire({
                        icon: 'success',
                        title: 'Success!',
                        text: res.data.response,
                    });
                    console.log(res.data);
                    setCandidates(res.data);
                }
            });
    }

    const handleChangePhraseQuery = () => {
        setPhraseQuery(!phraseQuery);
      };

    return (
        <><div className="h-100 d-flex justify-content-center align-items-center container rounded bg-white">
            <div className="">
                <div className="md-5">
                    <div className="p-3 py-5">
                        <div className="mb-3">
                            <h4 className="text-right">Pretraga</h4>
                        </div>
                        <div className="row mt-3">
                            <div className="col-md-12">
                                <label className="labels">Kategorija</label>
                                <select id="InputCategory"
                                    name="category"
                                    className="form-control"
                                    value={category}
                                    onChange={(e) => setCategory(e.target.value)}
                                >
                                    <option value="" disabled>Odaberite kategoriju</option>
                                    <option value="firstname and lastname">Ime i prezime</option>
                                    <option value="education">Strucna sprema</option>
                                    <option value="cvContent">Sadrzaj CV</option>
                                    <option value="clContent">Sadrzaj propratnog pisma</option>
                                </select>
                            </div>
                        </div>
                        {category!="education" && category != "" && <div className="row mt-3">
                            <div className="col-md-12">
                                <label className="labels">Tekst</label>
                                <input type="text" className="form-control" placeholder="..." value={text} onChange={(e) => setText(e.target.value)} />
                            </div>
                        </div>}
                        {category=="education" && category != "" && <div className="row mt-3">
                            <div className="col-md-12">
                                <label className="labels">Stepen</label>
                                <select id="InputStepen"
                                    name="degree"
                                    className="form-control"
                                    value={degree}
                                    onChange={(e) => setDegree(e.target.value)}
                                >
                                    <option value="" disabled>Odaberite stepen strucne spreme</option>
                                    <option value="I">I Stepen (cetiri razreda osnovne)</option>
                                    <option value="II">II Stepen (osnovna skola)</option>
                                    <option value="III">III Stepen (SSS srednja skola)</option>
                                    <option value="IV">IV Stepen (SSS srednja skola)</option>
                                    <option value="V">V Stepen (VKV - SSS srednja skola)</option>
                                    <option value="VI">VI Stepen (VSS visa skola)</option>
                                    <option value="VII">VII (Visoka strusna sprema)</option>
                                    <option value="VIII">VIII (Doktor nauka)</option>
                                </select>
                            </div>
                        </div>}
                        <div className="row mt-3">
                            <div className="col-md-12">
                                <div className="form-check">
                                    <input className="form-check-input" type="checkbox" value={phraseQuery} onChange={handleChangePhraseQuery} id="flexCheckDefault" />
                                    <label className="form-check-label">
                                    Phrase Query
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div className="mt-5 text-center">
                            {isPending && <label>Pretraga je u toku...</label>}
                            {!isPending && <button onClick={(e) => onSubmit(e)} disabled={Validate()} className="btn btn-primary profile-button" type="button">Potvrda</button>}
                        </div>
                    </div>
                </div>
            </div>
            
        </div>
        {candidates && 
            <div className="m-5 p-4">
                <CandidateList candidates={candidates} />
            </div>}
        </>
    );
}

export default Search;