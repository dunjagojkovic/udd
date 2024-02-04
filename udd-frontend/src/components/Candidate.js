import { useState } from "react";

const Candidate = ({candidate}) => {

    return (
        <><div className="h-100 d-flex justify-content-center align-items-center container rounded bg-white">
            {candidate && <div className="">
                <div className="md-5">
                    <div className="p-3 py-5">
                        <div className="row mt-3">
                            <div className="col-md-6">
                                <label className="labels">Ime</label>
                                <input type="text" className="form-control" defaultValue={candidate.firstName} readOnly/>
                            </div>
                            <div className="col-md-6">
                                <label className="labels">Prezime</label>
                                <input type="text" className="form-control" defaultValue={candidate.lastName} readOnly />
                            </div>
                        </div>   
                        <div className="row mt-3">
                            <div className="col-md-12">
                                <label className="labels">Stepen</label>
                                <select id="InputStepen"
                                    name="degree"
                                    className="form-control"
                                    defaultValue={candidate.education}
                                   disabled
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
                            <div className="col-md-12">
                                <label className="labels">Highlight</label>     
                                <p>{candidate.highlight}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>}
        </div>
        <hr></hr>
        </>
    );
}

export default Candidate;