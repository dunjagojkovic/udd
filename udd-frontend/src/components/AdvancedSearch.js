import { useState } from "react";
import Swal from "sweetalert2";

import axios from 'axios';
import AdvancedSearchCondition from "./AdvancedSearchCondition";
import CandidateList from "./CandidateList";

const AdvancedSearch = () => {
    const [loading, setLoading] = useState(false);
    const [isPending, setIsPending] = useState(false);
    const [conditions, setConditions] = useState([{
        category: "",
        content: "",
        phraseQuery: false,
        operator: ""
    }]);
    const [candidates, setCandidates] = useState();

    const Validate = () => {
        /*if (institutionName === "" || institutionType === ""
        || title === "" || gpa === "" 
        || startDate === "" || endDate === "") {
            Swal.fire({
                icon: 'warning',
                title: 'Oops...',
                text: 'All inputs must be filled!',
            });
            return false;
        }
        if (startDate >= endDate) {
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'Start date must be before end date!',
            });
            return false;
        }*/
        return true;
    }

    const onSubmit = async (e) => {
        e.preventDefault();
        if (!Validate())
            return;

        setIsPending(true);

        let educationUpdate = []

        /*education.map((ed) => {
            educationUpdate = educationUpdate.concat({
                "title": ed.title,
                "gpa": ed.gpa,
                "institutionName": ed.institutionName,
                "institutionType": ed.institutionType,
                "startDate": FormatDate(new Date(ed.startDate)),
                "endDate": FormatDate(new Date(ed.endDate))
            })
        })
        educationUpdate = educationUpdate.concat({
            "title": title,
            "gpa": gpa,
            "institutionName": institutionName,
            "institutionType": institutionType,
            "startDate": FormatDate(startDate),
            "endDate": FormatDate(endDate)
        })
*/
        console.log(conditions);
        axios.post(axios.defaults.baseURL + 'search/advanced', {conditions: conditions})
            .then(res => {
                setIsPending(false);
                Swal.fire({
                    icon: 'success',
                    title: 'Success!',
                    text: res.data.response,
                });
                console.log(res);
                setCandidates(res.data);
            });
    }

    const numberOfElements =  () => {
        return conditions.length;
    }

    const removeConditionChanger = (index) => remove(index);
    const addConditionChanger = () => add();
    const updateConditionChanger = (condition) => update(condition);

    const update = (updateCondition) => {
        setLoading(true);
        let conditionsUpdate = []
        conditions.map((condition, checkIndex) => {
            if (checkIndex == updateCondition.index)
            conditionsUpdate = conditionsUpdate.concat({
                "category": updateCondition.category,
                "content": updateCondition.content,
                "phraseQuery": updateCondition.phraseQuery,
                "operator": updateCondition.operator,
            })
            else{
                conditionsUpdate = conditionsUpdate.concat({
                    "category": condition.category,
                    "content": condition.content,
                    "phraseQuery": condition.phraseQuery,
                    "operator": condition.operator,
                })
            }
        })
        setConditions([]);
        setConditions(conditionsUpdate);
        setLoading(false);
    }

    const add = () => {
        setLoading(true);
        let conditionsUpdate = conditions
        setConditions([]);
        conditionsUpdate = conditionsUpdate.concat({
                "category": "",
                "content": "",
                "phraseQuery": false,
                "operator": "",
        })
        setConditions(conditionsUpdate);
        setLoading(false);
    }

    const remove = (index) => {
        setLoading(true);
        let conditionsUpdate = []
        let conditions2 = conditions
        setConditions([]);
        conditions2.map((condition, checkIndex) => {
            console.log(checkIndex, index, checkIndex != index)
            if (checkIndex != index)
            conditionsUpdate = conditionsUpdate.concat({
                "category": condition.category,
                "content": condition.content,
                "phraseQuery": condition.phraseQuery,
                "operator": condition.operator,
            })
        })
        console.log(conditionsUpdate)
        setConditions(conditionsUpdate);
        setLoading(false);
    }

    return (
        <div className="container rounded bg-white mt-5 mb-5">
            <div className="row">
                <div className="col-md-5 border-right">
                    <div className="p-3 py-5">
                        <div className="d-flex justify-content-between align-items-center mb-3">
                            <h4 className="text-right">Slozena pretraga</h4>
                        </div>
                        {!loading && conditions && conditions.map((condition, index) => {
                            return (
                                <AdvancedSearchCondition conditions={conditions} key={index} numOfElements={numberOfElements()} removeConditionChanger={removeConditionChanger}
                                addConditionChanger={addConditionChanger} updateConditionChanger={updateConditionChanger} conditionIndex={index}/>
                            )
                        })
                        }
                        <div className="mt-5 text-center">
                            {isPending && <label>Pretraga je u toku...</label>}
                            {!isPending && <button onClick={(e) => onSubmit(e)} className="btn btn-primary profile-button" type="button">Potvrda</button>}
                        </div>
                    </div>
                </div>
            </div>
            <div className="row">
            {candidates && 
            <div className="m-5 p-4">
                <CandidateList candidates={candidates} />
            </div>}
            </div>
        </div>
    );
}

export default AdvancedSearch;