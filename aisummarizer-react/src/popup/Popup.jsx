import React, { useState } from "react";

function Popup() {
  const [summary, setSummary] = useState("");
  const [file, setFile] = useState(null);
  const [loading, setLoading] = useState(false);

  const handleSubmit = async () => {
    if (!file) return;
    const formData = new FormData();
    formData.append("file", file);

    setLoading(true);
    const response = await fetch("http://localhost:8080/api/summarize/file", {
      method: "POST",
      body: formData,
    });
    const result = await response.text();
    setSummary(result);
    setLoading(false);
  };

  return (
    <div style={{ padding: 10, width: 300 }}>
      <h3>AI Summarizer</h3>
      <input
        type="file"
        onChange={(e) => setFile(e.target.files[0])}
        accept=".pdf,.docx,.jpg,.jpeg,.png,.txt"
      />
      <button onClick={handleSubmit} disabled={!file || loading}>
        {loading ? "Summarizing..." : "Summarize"}
      </button>
      {summary && (
        <div>
          <h4>Summary:</h4>
          <p>{summary}</p>
        </div>
      )}
    </div>
  );
}

export default Popup;