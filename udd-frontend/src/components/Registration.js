import { useState } from "react";
import Swal from "sweetalert2";

import axios from 'axios';

const Registration = () => {
    const [firstname, setFirstname] = useState("");
    const [lastname, setLastname] = useState("");
    const [street, setStreet] = useState("");
    const [city, setCity] = useState("");
    const [education, setEducation] = useState("");
    const [cv, setCv] = useState();
    const [coverLetter, setCoverletter] = useState();
    const [isPending, setIsPending] = useState(false);

    const Validate = () => {
        if(firstname=="" || lastname=="" || street=="" || city=="" || education=="" || cv==null || coverLetter==null){
            return true;
        }
        return false
    }

    const onSubmit = async (e) => {
        e.preventDefault();

        setIsPending(true);

        const cvFileInput = document.querySelector("#cvInput");
        const coverLetterFileInput = document.querySelector("#coverLetterInput");

        let formData = new FormData();
        formData.append('firstName', firstname);
        formData.append('lastName', lastname);
        formData.append('street', street);
        formData.append('city', city);
        formData.append('education', education);
        formData.append('cv', cvFileInput.files[0]);
        formData.append('coverLetter', coverLetterFileInput.files[0]);

        axios.post(axios.defaults.baseURL + 'candidate/registration', formData)
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
                }
            });
    }

    return (
        <div className="h-100 d-flex justify-content-center align-items-center container rounded bg-white">
            <div className="">
                <div className="md-5">
                    <div className="p-3 py-5">
                        <div className="mb-3">
                            <h4 className="text-right">Registracija kandidata</h4>
                        </div>
                        <div className="row mt-3">
                            <div className="col-md-6">
                                <label className="labels">Ime</label>
                                <input type="text" className="form-control" value={firstname} onChange={(e) => setFirstname(e.target.value)} />
                            </div>
                            <div className="col-md-6">
                                <label className="labels">Prezime</label>
                                <input type="text" className="form-control" value={lastname} onChange={(e) => setLastname(e.target.value)} />
                            </div>
                        </div>
                        <div className="row mt-3">
                            <div className="col-md-6">
                                <label className="labels">Ulica</label>
                                <input type="text" className="form-control" value={street} onChange={(e) => setStreet(e.target.value)} />
                            </div>
                            <div className="col-md-6">
                                <label className="labels">Grad</label>
                                <input type="text" className="form-control" value={city} onChange={(e) => setCity(e.target.value)} />
                            </div>
                        </div>
                        <div className="row mt-3">
                            <div className="col-md-12">
                                <label className="labels">Stepen</label>
                                <select id="InputStepen"
                                    name="degree"
                                    className="form-control"
                                    value={education}
                                    onChange={(e) => setEducation(e.target.value)}
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
                        </div>
                        <div className="row mt-3">
                            <div className="col-md-6">
                                <label className="labels">CV</label>
                                <input enctype="multipart/form-data" type="file" accept="application/pdf" className="form-control" id="cvInput" value={cv} onChange={(e) => setCv(e.target.value)} />
                            </div>
                            <div className="col-md-6">
                                <label className="labels">Propratno pismo</label>
                                <input enctype="multipart/form-data" type="file" accept="application/pdf" className="form-control" id="coverLetterInput" value={coverLetter} onChange={(e) => setCoverletter(e.target.value)} />
                            </div>
                        </div>
                        <div className="mt-5 text-center">
                            {isPending && <label>Registracija je u toku...</label>}
                            {!isPending && <button onClick={(e) => onSubmit(e)} disabled={Validate()} className="btn btn-primary profile-button" type="button">Potvrda</button>}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Registration;